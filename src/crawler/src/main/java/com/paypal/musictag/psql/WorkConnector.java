package com.paypal.musictag.psql;
import java.sql.*;
import java.util.*;

import com.paypal.musictag.exception.NoNextException;
import com.paypal.musictag.util.CrawlerUtil;

final public class WorkConnector {

    private final static String hostname = "10.24.53.72";
    private final static int port = 5432;
    private final static String dbname = "musicbrainz_db";
    private final static String username = "musicbrainz";
    private final static String password = "musicbrainz";

    private final static String offsetFile = "logs/work.log";

    private final static String workQueryStr =
        "SELECT * \n" +
        "FROM work \n" +
        "where exists ( select * from l_url_work luw where luw.entity1 = work.id) \n" +
        "order by id";

    private final static String noUrlWorkQueryStr =
        "select * \n" +
        "FROM work where not exists ( select * from l_url_work luw where luw.entity1 = work.id) \n" +
        "order by id offset ? limit ?";

    private final static String urlQueryStr =
        "select url.url\n" +
        "from url\n" +
        "join l_url_work as luw on url.id = luw.entity0\n" +
        "join link on link.id = luw.link\n" +
        "join link_type as lt on lt.id = link.link_type\n" +
        "where lt.id = 271 and luw.entity1 = ?";

    private final static String recordingQueryStr =
        "SELECT recording.* FROM l_recording_work\n" +
        "JOIN recording ON entity0 = recording.id\n" +
        "WHERE entity1 = ?";

    private Connection connection;
    private PreparedStatement noUrlWorkQuery;
    private PreparedStatement urlQuery;
    private PreparedStatement recordingQuery;
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
    public WorkConnector() throws SQLException, ClassNotFoundException {

        readOffset();

        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + dbname, username, password);
        noUrlWorkQuery = connection.prepareStatement(noUrlWorkQueryStr);
        urlQuery = connection.prepareStatement(urlQueryStr);
        recordingQuery = connection.prepareStatement(recordingQueryStr);
    }

    private void readOffset() {
        offset = CrawlerUtil.readInt(offsetFile);
        seq += offset;
    }

    private void saveOffset() {
        CrawlerUtil.writeInt(offsetFile, offset);
    }


    public List<Map<String, Object>> allWorkHavingLyricUrl() throws SQLException {
        Statement q = connection.createStatement();
        return resultSetToList(q.executeQuery(workQueryStr));
    }

    /**
     * Thread safe. Return the next work (include gid, id, work-amount).
     *
     * @return
     * @throws SQLException
     * @throws NoNextException
     */
    synchronized public Map<String, Object> nextNoUrlWork() throws SQLException, NoNextException {

        // If no work cached, fetch the artist information from database.
        if (resultSet == null || !resultSet.next()) {

            noUrlWorkQuery.setInt(1, offset);
            noUrlWorkQuery.setInt(2, cacheAmount);

            resultSet = noUrlWorkQuery.executeQuery();

            saveOffset();

            offset += cacheAmount;

            // Still no artist
            if (!resultSet.next()) {
                throw new NoNextException();
            }
        }

        Map<String, Object> map = resultSetToHashMap(resultSet);
        map.put("seq", ++seq);
        return map;
    }

    synchronized public List<String> findAllLyricUrls(int workId) throws SQLException {
        urlQuery.setInt(1, workId);
        ResultSet rs = urlQuery.executeQuery();
        List<String> arr = new ArrayList<String>();
        while(rs.next()) {
            arr.add(rs.getString(1));
        }
        return arr;
    }

    synchronized public List<Map<String, Object>> findAllRecordings(int workId) throws SQLException {
        recordingQuery.setInt(1, workId);
        ResultSet rs = recordingQuery.executeQuery();
        return resultSetToList(rs);
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

    public static void main(String[] args) throws ClassNotFoundException, SQLException, NoNextException {
        WorkConnector workConnector = new WorkConnector();
        Map<String, Object> data = workConnector.nextNoUrlWork();
        List<String> urls = workConnector.findAllLyricUrls((int)data.get("id"));
    }
}
