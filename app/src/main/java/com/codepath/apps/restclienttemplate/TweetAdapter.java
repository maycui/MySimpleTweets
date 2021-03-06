package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by mayc on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    public List<Tweet> mTweets;
    Context context;
    public final String SCREEN_NAME = "screenname";
    public final String TWEET_ID = "tweetid";
    private TwitterClient client;


    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {
        this.mTweets = tweets;
    }

    // for each row, inflate the layout and cache references into ViewHolder
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        client = TwitterApp.getRestClient();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Tweet tweet = mTweets.get(position);
        holder.tvUsername.setText(tweet.getUser().getName());
        holder.screenName.setText("@" + tweet.getUser().getScreenName());
        holder.tvBody.setText(tweet.getBody());
        holder.time.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        //setting the likes, retweets, and other info
        holder.retweetN.setText(String.valueOf(tweet.getRetweetCount()));
        holder.likeN.setText(String.valueOf(tweet.getFavoritesCount()));
        holder.replyN.setText("");

        //setting tints
        int highlightColor = context.getResources().getColor(R.color.grey);
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(highlightColor, PorterDuff.Mode.SRC_ATOP);

        int blueColor = context.getResources().getColor(R.color.brightblue);
        final PorterDuffColorFilter bluefilter = new PorterDuffColorFilter(highlightColor, PorterDuff.Mode.SRC_ATOP);

        holder.like.getDrawable().setColorFilter(colorFilter);


        holder.retweet.getDrawable().setColorFilter(colorFilter);


        holder.dm.getDrawable().setColorFilter(colorFilter);


        //set profile image
        Glide.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(holder.ivProfileImage);

        //set media image
        if (tweet.getMediaURL() != null) {
            Glide.with(context)
                    .load(tweet.getMediaURL())
                    .bitmapTransform(new RoundedCornersTransformation(context, 15, 0))
                    .into(holder.media);
        } else {
            holder.media.setVisibility(View.GONE);
        }


        //reply
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.getUser().getScreenName() != null) {
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra(SCREEN_NAME, tweet.getUser().getScreenName());
                    intent.putExtra(TWEET_ID, tweet.getUid());
                    context.startActivity(intent);
                }
            }
        });

        holder.retweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.getUser().getScreenName() != null) {
                    tweet.retweeted = "true";
                    Intent i = new Intent(context, ComposeActivity.class);
                    i.putExtra("tweet", tweet.getUid());
                    i.putExtra("body", tweet.getBody());
                    context.startActivity(i);
                }

            }
        });

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.getUser().getScreenName() != null) {
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("userID", tweet.getUser().getUid());
                    i.putExtra("NAME", tweet.getUser().getScreenName());
                    context.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView screenName;
        public TextView time;
        public ImageView reply;
        public ImageView like;
        public ImageView retweet;
        public ImageView dm;
        public TextView replyN;
        public TextView likeN;
        public TextView retweetN;
        public ImageView media;

        public ViewHolder(View itemView) {
            super(itemView);
            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.user);
            tvBody = (TextView) itemView.findViewById(R.id.text);
            screenName = (TextView) itemView.findViewById(R.id.screen);
            time = (TextView) itemView.findViewById(R.id.time);
            reply = (ImageView) itemView.findViewById(R.id.reply);
            like = (ImageView) itemView.findViewById(R.id.like);
            retweet = (ImageView) itemView.findViewById(R.id.rt);
            dm = (ImageView) itemView.findViewById(R.id.dm);
            replyN = (TextView) itemView.findViewById(R.id.replyN);
            likeN = (TextView) itemView.findViewById(R.id.likeN);
            retweetN = (TextView) itemView.findViewById(R.id.rtN);
            media = (ImageView) itemView.findViewById(R.id.mediaD);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Tweet tweet = mTweets.get(position);
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        }


    }


}
