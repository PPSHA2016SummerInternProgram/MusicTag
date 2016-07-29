package com.paypal.musictag.mongo;

import java.net.UnknownHostException;

public final class ArtistConnector extends MongoConnector {

	private final static String artistTableName = MongoCollectionName.LAST_FM_ARTIST.getName();
	private final static String artistNotFoundTableName = MongoCollectionName.LAST_FM_ARTIST_NOF_FOUND.getName();

	public ArtistConnector() throws UnknownHostException {
		super(artistTableName, artistNotFoundTableName);
	}

	public String getArtistTableName() {
		return artistTableName;
	}

	public String getArtistNotFoundTablename() {
		return artistNotFoundTableName;
	}

}
