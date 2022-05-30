package net.toujoustudios.antithreat.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This file has been created by Ian Toujou.
 * Project: AntiThreat
 * Date: 30/05/2022
 * Time: 16:32
 */
public class LinkUtil {

    public static String getBaseURL(String url) {
        try {
            URL searchUrl = new URL(url);
            return searchUrl.getProtocol() + "://" + searchUrl.getHost();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getHost(String url) {
        try {
            URL searchUrl = new URL(url);
            return searchUrl.getHost();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getHost(URL url) {
        return url.getHost();
    }

}
