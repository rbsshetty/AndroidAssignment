package com.rajeshshetty.mymovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Movies implements Parcelable{
    private String title;
    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;
    private int vote_count;
    private Float vote_average;


    @Override
    public String toString() {
        return "title=" + title + ", poster_path=" + poster_path + ", original_title=" + original_title + ", overview=" + overview
                + ", release_date=" + release_date + ", vote_count=" + vote_count + ", vote_average=" + vote_average ;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public static ArrayList<Movies> parseMovieList(JSONArray jsonArray){
        ArrayList<Movies> moviesArrayList =new ArrayList<>();
        Gson gson = new GsonBuilder().create();

        for(int i=0;i<jsonArray.length();i++){

            try {
                Movies movies = gson.fromJson(jsonArray.get(i).toString(), Movies.class);
                moviesArrayList.add(movies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return moviesArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Movies(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_count=in.readInt();
        vote_average=in.readFloat();
    }

    Movies(){
    }
    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeString(poster_path);
            parcel.writeString(original_title);
            parcel.writeString(overview);
            parcel.writeString(release_date);
            parcel.writeInt(vote_count);
            parcel.writeFloat(vote_average);
    }
}