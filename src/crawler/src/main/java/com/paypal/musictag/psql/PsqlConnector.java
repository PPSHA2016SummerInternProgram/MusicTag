package com.paypal.musictag.psql;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.paypal.musictag.exception.NoArtistException;
import com.paypal.musictag.mongo.ArtistConnector;
import com.paypal.musictag.util.CrawlerUtil;

final public class PsqlConnector {

	private final static String hostname = "10.24.53.72";
	private final static int port = 5432;
	private final static String dbname = "musicbrainz_db";
	private final static String username = "musicbrainz";
	private final static String password = "musicbrainz";

	private final static String offsetFile = "logs/artist.log";

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet = null;
	private int cacheAmount = 100;
	private int offset = 0;
	private int seq = 0;

	/**
	 * Connection to database and sustain a connection and statement.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public PsqlConnector() throws SQLException, ClassNotFoundException {

		readOffset();

		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + dbname, username,
				password);
		statement = connection.createStatement();
	}

	private void readOffset() {
		offset = CrawlerUtil.readInt(offsetFile);
		seq += offset;
	}

	private void saveOffset() {
		CrawlerUtil.writeInt(offsetFile, offset);
	}

	synchronized public List<Map<String, Object>> findAllRecordings(String releaseGid) throws SQLException {
		UUID id = UUID.fromString(releaseGid);
		String query = "SELECT recording.gid\n" + "FROM recording\n" + "WHERE recording.id IN\n"
				+ "(SELECT track.recording\n" + "FROM track\n" + "WHERE track.medium IN\n" + "(SELECT medium.id\n"
				+ "FROM medium\n" + "LEFT JOIN release\n" + "ON medium.release = release.id\n" + "WHERE release.gid = '"
				+ id + "'\n" + "));";
		return resultSetToList(statement.executeQuery(query));

	}

	synchronized public List<Map<String, Object>> findAllReleases(String artistGid) throws SQLException {
		UUID id = UUID.fromString(artistGid);
		String query = "SELECT DISTINCT ON (release.id)\n" + "    release.gid\n" + "FROM release\n"
				+ "JOIN artist_credit_name acn ON acn.artist_credit = release.artist_credit\n"
				+ "JOIN artist on acn.artist = artist.id\n" + "WHERE artist.gid = '" + id + "'";
		return resultSetToList(statement.executeQuery(query));
	}

	synchronized public List<Map<String, Object>> countArtistRecordsNum() throws SQLException {

		String query = "select artist_credit, artist.gid as gid, count(artist_credit) as artist_count from recording\n"
				+ "join artist on artist.id = recording.artist_credit\n"
				+ "where artist_credit <= 1400000 and artist_credit > 0 group by artist_credit , artist.gid;";
		return resultSetToList(statement.executeQuery(query));
	}

	public int countryAmount(String gid) throws SQLException {
		String query = "select count(distinct rc.country) from release\n"
				+ "JOIN artist_credit_name acn ON release.artist_credit = acn.artist_credit\n"
				+ "JOIN artist on acn.artist = artist.id\n" + "JOIN release_country rc ON release.id = rc.release\n"
				+ "where artist.gid = '" + gid + "';\n";
		return getAmount(query);
	}

	private int getAmount(String query) throws SQLException {
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return 0;
	}

	public int releaseAmount(String gid) throws SQLException {
		String query = "SELECT count(*) AS release_count\n" + "FROM release\n"
				+ "JOIN artist_credit_name acn ON release.artist_credit = acn.artist_credit\n"
				+ "JOIN artist on acn.artist = artist.id\n" + "WHERE artist.gid = '" + gid + "';\n";
		return getAmount(query);
	}

	public int recordingAmount(String gid) throws SQLException {
		String query = "SELECT count(*) AS recording_count\n" + "FROM recording\n"
				+ "JOIN artist_credit_name acn ON recording.artist_credit = acn.artist_credit\n"
				+ "JOIN artist on acn.artist = artist.id\n" + "WHERE artist.gid = '" + gid + "';\n";
		return getAmount(query);
	}

	public int friendsAmount(String gid) throws SQLException {
		String query = "select count(distinct acn1.artist)\n" + "from artist_credit_name as acn \n"
				+ "join artist as ar on acn.artist = ar.id\n"
				+ "join artist_credit_name as acn1 on acn.artist_credit = acn1.artist_credit\n"
				+ "join artist as ar1 on ar1.id = acn1. artist\n" + "where ar.gid = '" + gid + "'\n"
				+ "and ar1.gid != '" + gid + "';\n";
		return getAmount(query);
	}

	public int activeRange(String gid) throws SQLException {
		String query = "select min(re.date_year) as start, max(re.date_year) as end from release\n"
				+ "JOIN artist_credit_name acn ON release.artist_credit = acn.artist_credit\n"
				+ "JOIN artist on acn.artist = artist.id\n" + "JOIN release_event re on re.release = release.id\n"
				+ "where artist.gid = '" + gid + "'\n" + "and re.date_year is not NULL;\n";

		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			int start = resultSet.getInt(1);
			int end = resultSet.getInt(2);
			return end - start + 1;
		}
		return 0;
	}

	/**
	 * Thread safe. Return the next artist (include gid, id, work-amount).
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NoArtistException
	 */
	synchronized public Map<String, Object> nextArtist() throws SQLException, NoArtistException {

		// If no artist cached, fetch the artist information from database.
		if (resultSet == null || !resultSet.next()) {
			String query = "with a as (select gid, id from artist limit " + cacheAmount + " offset " + offset + ")\n"
					+ "select a.*, count(work) from a\n" + "left join\n" + "(SELECT * FROM (\n"
					+ "	SELECT entity1 AS work, acn.artist as id\n" + "    FROM l_recording_work\n"
					+ "    JOIN recording ON recording.id = entity0\n"
					+ "    JOIN artist_credit_name acn ON acn.artist_credit = recording.artist_credit\n"
					+ "    WHERE acn.artist in (select id from a)\n" + "UNION\n"
					+ "    SELECT entity1 AS work, entity0 as id\n" + "    FROM l_artist_work ar\n"
					+ "    JOIN link ON ar.link = link.id\n" + "    JOIN link_type lt ON lt.id = link.link_type\n"
					+ "    WHERE entity0 in (select id from a)\n" + ") s\n" + ") t\n" + "on a.id = t.id\n"
					+ "group by a.gid, a.id\n" + ";";

			query = "select gid, id, 0 as count from artist order by gid limit " + cacheAmount + " offset " + offset;

			resultSet = statement.executeQuery(query);

			saveOffset();

			offset += cacheAmount;

			// Still no artist
			if (!resultSet.next()) {
				throw new NoArtistException();
			}
		}

		++seq;

		Map<String, Object> map = resultSetToHashMap(resultSet);
		map.put("seq", seq);
		return map;
	}

	/**
	 * Convert a result set to list.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();
		while (rs.next()) {
			list.add(resultSetToHashMap(rs));
		}
		return list;
	}

	/**
	 * Convert a result set to map.
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Map<String, Object> resultSetToHashMap(ResultSet rs) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		Map<String, Object> map = new HashMap<>();
		for (int i = 1; i <= columns; i++) {
			map.put(metadata.getColumnName(i), rs.getObject(i));
		}
		return map;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoArtistException, IOException {
		// String artistGid = "20244d07-534f-4eff-b4d4-930878889970";
		PsqlConnector psqlConnector = new PsqlConnector();
		// List<?> list = psqlConnector.countArtistRecordsNum();
		ArtistConnector artistConnector = new ArtistConnector();
		BufferedWriter writer = new BufferedWriter(new FileWriter("logs/statistics.csv"));
		while (true) {
			try {
				Map<String, Object> artist = artistConnector.nextArtist();
				String artistGid = String.valueOf(artist.get("gid"));
				writer.write(artistGid + " ");
				int amount = psqlConnector.countryAmount(artistGid);
				writer.write(amount + " ");
				amount = psqlConnector.releaseAmount(artistGid);
				writer.write(amount + " ");
				amount = psqlConnector.recordingAmount(artistGid);
				writer.write(amount + " ");
				amount = psqlConnector.friendsAmount(artistGid);
				writer.write(amount + " ");
				amount = psqlConnector.activeRange(artistGid);
				writer.write(amount + " ");
				int arr[] = artistConnector.findListenersAndPlaycount(artistGid);
				writer.write(arr[0] + " ");
				writer.write(arr[1]);
				writer.write("\n");
			} catch (NoArtistException noArtist) {
				break;
			}
		}
		writer.close();

		// List<?> list =
		// psqlConnector.findAllReleases("3ff72a59-f39d-411d-9f93-2d4a86413013");
		// System.out.println(list);
		// for (int i = 0; i < 1000; i++) {
		// Map<?, ?> artist = psqlConnector.nextArtist();
		// System.out.println(i + ": " + artist.get("gid") + ", " +
		// artist.get("id") + ", " + artist.get("count")
		// + ", " + artist.get("seq"));
		// }
	}

}
