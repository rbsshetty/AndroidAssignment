package com.rajeshshetty.mymovies.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rajeshshetty.mymovies.AppConstants;
import com.rajeshshetty.mymovies.Model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Rajesh.
 */
/* Get the data from server. */
public class APIManager {


    private static final String TAG_NAME="APIManager";
    private static final String MOVIE_REQUEST    = "movie_request";


    public ImageLoader.ImageCache imageCache;



    public static class BitmapLruCache
            extends LruCache<String, Bitmap>
            implements ImageLoader.ImageCache {

        public BitmapLruCache() {
            this(getDefaultLruCacheSize());
        }

        public BitmapLruCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        public static int getDefaultLruCacheSize() {
            final int maxMemory =
                    (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;

            return cacheSize;
        }
    }

    /**
     * Interface for getting the response from the APIs
     * @param <T> Type of object received as response
     */
    public interface Callback<T> {
        void onSuccess(T param);
        void onFailure(JSONObject param);
    }

    private static APIManager mInstance = null;

    public static synchronized APIManager getInstance(Context context) {
        if ( mInstance == null ) {
            mInstance = new APIManager(context);
        }

        return mInstance;
    }

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    private APIManager(Context context) {
        mContext = context.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(mContext);
        mRequestQueue.start();

        imageCache = new BitmapLruCache();
        mImageLoader = new ImageLoader(mRequestQueue, imageCache);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


    public void removeRequest(String Tag){
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(Tag);
        }
    }


    public String getBaseUrlFromPreference(){
        return AppConstants.BASE_URL;
    }

    public String getAPIKey(){
        return "b05a4a2bf18b75f175c229c141d7f6b8";
    }

    /* Check if network is connected or not. */
    public boolean isNetworkAvailble()
    {
        ConnectivityManager connMgr = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public String getMovieList(final Callback<ArrayList<Movies>> callback){

        String getMovieListURL = UrlConfig.getMovieList(getBaseUrlFromPreference())+"?sort_by=popularity.desc&api_key="+getAPIKey();
        StringRequest req = new StringRequest(Request.Method.GET, getMovieListURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                    JSONArray jsonArray = json.getJSONArray("results");
                    callback.onSuccess(Movies.parseMovieList(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG_NAME,error.toString());
            }
        });

        req.setTag(MOVIE_REQUEST);

        mRequestQueue.add(req);

        return MOVIE_REQUEST;
    }





}
