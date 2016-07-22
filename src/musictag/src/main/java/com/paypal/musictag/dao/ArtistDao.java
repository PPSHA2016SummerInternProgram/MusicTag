package com.paypal.musictag.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.paypal.musictag.dao.usingwebservice.exception.NetConnectionException;
import com.paypal.musictag.dao.usingwebservice.exception.NetContentNotFoundException;

public interface ArtistDao {

    /**
     * Find profile for artist.
     * 
     * @param artistGid
     * @return
     * @throws NetConnectionException 
     */
    Map<String, Object> profile(String artistGid) throws NetConnectionException;

    /**
     * Find rel-links for artist.
     * 
     * @param artistGid
     * @return
     * @throws ProtocolException 
     * @throws MalformedURLException 
     * @throws JsonMappingException 
     * @throws NetContentNotFoundException 
     * @throws NetConnectionException 
     */
    Map<String, Object> relLinks(String artistGid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

    /**
     * Find the image src for artist.
     * 
     * @param artistGid
     * @return
     * @throws NetConnectionException 
     * @throws IOException
     */
    Map<String, Object> image(String artistGid) throws NetConnectionException;

    /**
     * Find basic information of an artist by his/her global ID.
     * Basic information may includes Area, Country, Type, Gender, ID, Name, Life-Span and so on.
     * If cannot get the information, throw an Exception.
     * 
     * @param artistGid
     * @return
     * @throws ProtocolException 
     * @throws MalformedURLException 
     * @throws JsonMappingException 
     * @throws NetContentNotFoundException 
     * @throws NetConnectionException 
     * @throws IOException
     */
    Map<String, Object> basicInfo(String artistGid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;

    /**
     * Find all release-groups (Albums) of an artist by his/her global ID.
     * Result includes "release-group-count" for albums amount.
     * An album includes title, primary-type, first-release-date, ID and so on.
     * 
     * @param artistGid
     * @return
     * @throws ProtocolException 
     * @throws MalformedURLException 
     * @throws JsonMappingException 
     * @throws NetContentNotFoundException 
     * @throws NetConnectionException 
     * @throws IOException
     */
    Map<String, Object> releaseGroup(String artistGid) throws NetConnectionException, NetContentNotFoundException, JsonMappingException, MalformedURLException, ProtocolException;
}
