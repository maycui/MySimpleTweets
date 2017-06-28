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
    public String createdAt;


    protected Tweet(Parcel in) {
        body = in.readString();
        uid = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        createdAt = in.readString();
    }

    public Tweet() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeParcelable(user, flags);
        dest.writeString(createdAt);
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
        return tweet;
    }

//    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
//        ArrayList<Tweet> results = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            try {
//                results.add(Tweet.fromJSON(jsonArray.getJSONObject(i)));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return results;
//    }



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
}
