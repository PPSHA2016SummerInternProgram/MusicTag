package com.paypal.musictag.mongo;

import java.net.UnknownHostException;

public class TrackConnector extends MongoConnector {

	private final static String trackTableName = MongoCollectionName.LAST_FM_TRACK.getName();
	private final static String trackNotFoundTableName = MongoCollectionName.LAST_FM_TRACK_NOT_FOUND.getName();

	public TrackConnector() throws UnknownHostException {
		super(trackTableName, trackNotFoundTableName);
	}

	public String getTrackTableName() {
		return trackTableName;
	}

	public String getTrackNotFoundTableName() {
		return trackNotFoundTableName;
	}

}
