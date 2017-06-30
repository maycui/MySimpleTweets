package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mayc on 6/26/17.
 */

public class Tweet implements Parcelable {
    public String body;
    public long uid;
    public User user;
    public String mediaURL;
    public String createdAt;
    public Integer retweetCount;
    public Integer favoritesCount;
    //public Integer replyCount;


    protected Tweet(Parcel in) {
        body = in.readString();
        uid = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        createdAt = in.readString();
        retweetCount = in.readInt();
        favoritesCount= in.readInt();
        mediaURL = in.readString();
        //replyCount = in.readInt();
    }

    public Tweet() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeParcelable(user, flags);
        dest.writeString(createdAt);
        dest.writeLong(retweetCount);
        dest.writeLong(favoritesCount);
        dest.writeString(mediaURL);
        //dest.writeLong(replyCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.favoritesCount = jsonObject.getInt("favorite_count");
        tweet.retweetCount = jsonObject.getInt("retweet_count");

        if (jsonObject.has("entities") && jsonObject.getJSONObject("entities").has("media")) {
            String url = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url");
            tweet.mediaURL = url + ":small";
        } else {
            tweet.mediaURL = null;
        }

        return tweet;
    }


    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public String getMediaURL() {
        return mediaURL;
    }
}
