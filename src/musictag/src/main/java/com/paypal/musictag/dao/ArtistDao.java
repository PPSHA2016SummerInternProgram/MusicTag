package com.paypal.musictag.dao;

import java.io.IOException;
import java.util.Map;

public interface ArtistDao {

    /**
     * Find rel-links for artist.
     * 
     * @param artistGid
     * @return
     * @throws IOException
     */
    Map<String, Object> relLinks(String artistGid) throws IOException;

    /**
     * Find the image src for artist.
     * 
     * @param artistGid
     * @return
     * @throws IOException
     */
    Map<String, Object> image(String artistGid) throws IOException;

    /**
     * Find basic information of an artist by his/her global ID.
     * Basic information may includes Area, Country, Type, Gender, ID, Name, Life-Span and so on.
     * If cannot get the information, throw an Exception.
     * 
     * @param artistGid
     * @return
     * @throws IOException
     */
    Map<String, Object> basicInfo(String artistGid) throws IOException;

    /**
     * Find all release-groups (Albums) of an artist by his/her global ID.
     * Result includes "release-group-count" for albums amount.
     * An album includes title, primary-type, first-release-date, ID and so on.
     * 
     * @param artistGid
     * @return
     * @throws IOException
     */
    Map<String, Object> releaseGroup(String artistGid) throws IOException;
}
