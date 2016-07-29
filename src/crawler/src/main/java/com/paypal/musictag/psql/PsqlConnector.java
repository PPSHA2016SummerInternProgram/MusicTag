package com.paypal.musictag.psql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

final public class PsqlConnector {

	private final static String hostname = "10.24.53.72";
	private final static int port = 5432;
	private final static String dbname = "musicbrainz_db";
	private final static String username = "musicbrainz";
	private final static String password = "musicbrainz";

	private final static String offsetFile = "logs/offset.log";

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
		Scanner reader = null;
		try {
			reader = new Scanner(new File(offsetFile));
			offset = reader.nextInt();
			seq += offset;
		} catch (Exception e) {
			// just empty, nothing to handle
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	private void saveOffset() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(offsetFile));
			writer.write(String.valueOf(offset));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
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

	public static void main(String[] args) throws ClassNotFoundException, SQLException, NoArtistException {
		PsqlConnector psqlConnector = new PsqlConnector();
		for (int i = 0; i < 1000; i++) {
			Map<?, ?> artist = psqlConnector.nextArtist();
			System.out.println(i + ": " + artist.get("gid") + ", " + artist.get("id") + ", " + artist.get("count")
					+ ", " + artist.get("seq"));
		}
	}
}
