package com.rajeshshetty.mymovies.Network;

/**
 * Created by Rajesh.
 */
public class UrlConfig {

    private static final String MOVIE_LIST = "discover/movie";

    public static String getMovieList(String URL){
        return URL +"/"+MOVIE_LIST;
    }
}

