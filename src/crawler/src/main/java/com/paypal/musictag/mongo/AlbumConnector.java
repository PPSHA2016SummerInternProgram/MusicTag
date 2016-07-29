package com.paypal.musictag.mongo;

import java.net.UnknownHostException;

public class AlbumConnector extends MongoConnector {

	private final static String albumTableName = MongoCollectionName.LAST_FM_ALBUM.getName();
	private final static String albumNotFoundTableName = MongoCollectionName.LAST_FM_ALBUM_NOT_FOUND.getName();

	public AlbumConnector() throws UnknownHostException {
		super(albumTableName, albumNotFoundTableName);
	}

	public String getAblumTableName() {
		return albumTableName;
	}

	public String getAlbumNotFoundTablename() {
		return albumNotFoundTableName;
	}
}
