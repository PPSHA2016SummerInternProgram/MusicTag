package com.paypal.musictag.crawler.musixmatch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlHelper {
   /**
	 * This method is used to get a parameter string from the Map.
	 *
	 * @param params key-value parameters
	 * @return A String containing the url parameter.
	 */
   public static String getURLQueryString( Map<String, Object> params) {
	   String paramString = "?";

	   try {
		   for (Map.Entry<String, Object> entry : params.entrySet()) {
			   paramString += entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), "UTF-8");

			   paramString += "&";
		   }
	   } catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
	   }

	   paramString = paramString.substring(0, paramString.length() - 1);

	   return paramString;
   }
}
