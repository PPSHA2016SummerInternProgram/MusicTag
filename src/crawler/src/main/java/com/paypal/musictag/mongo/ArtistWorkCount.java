package com.paypal.musictag.mongo;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.paypal.musictag.psql.PsqlConnector;

public final class ArtistWorkCount {
	private PsqlConnector psqlConnector;
	// private AnalysisArtistConnector artistConnector;
	private FileWriter fileWriter;
	private Map<String, Object> tmpMap;

	public ArtistWorkCount() throws ClassNotFoundException, SQLException {
		psqlConnector = new PsqlConnector();
	}

	public void Count() throws SQLException, IOException {
		List<Map<String, Object>> result = psqlConnector.countArtistRecordsNum();

		fileWriter = new FileWriter("c:\\Result1.txt");

		// artistConnector = new AnalysisArtistConnector();

		for (int i = 0; i < result.size(); i++) {

			tmpMap = result.get(i);

			fileWriter.write(String.valueOf((tmpMap).get("gid")) + ',' + String.valueOf((tmpMap).get("artist_count"))
					+ ',' + String.valueOf((tmpMap).get("artist_credit")) + '\n');

			// artistConnector.save(result.get(i));
		}

		System.out.println("done!");
	}

	static public void main(String[] argv) throws ClassNotFoundException, SQLException, IOException {
		ArtistWorkCount a = new ArtistWorkCount();
		a.Count();
	}
}