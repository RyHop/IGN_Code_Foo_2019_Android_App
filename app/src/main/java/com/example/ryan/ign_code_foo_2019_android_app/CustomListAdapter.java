package com.example.ryan.ign_code_foo_2019_android_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Article> {
    private String fragmentCaller;

    public CustomListAdapter(@NonNull Context context, @NonNull List<Article> objects, String whichFragment) {
        super(context, 0, objects);
        fragmentCaller = whichFragment;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listLayout = convertView;
        if (listLayout == null) {
            if (fragmentCaller.equals("ArticleFragment")) {
                listLayout = LayoutInflater.from(getContext()).inflate(
                        R.layout.listview_single_view, parent, false);
            } else {
                listLayout = LayoutInflater.from(getContext()).inflate(
                        R.layout.video_listview_single_view, parent, false);
            }
        }

        Article currentArticle = getItem(position);


        //Getting Minutes
        TextView minsTextView = listLayout.findViewById(R.id.minuteLabel);

        minsTextView.setText(currentArticle.getaDate());

        //Getting the headlines
        TextView headlineTextView = listLayout.findViewById(R.id.headlineText);

        headlineTextView.setText(currentArticle.getaTitle());

        //Set the Image using a different thread
        ImageView imageView = listLayout.findViewById(R.id.bitmapImage);

        //Gotten help from this website: https://medium.com/@crossphd/android-image-loading-from-a-string-url-6c8290b82c5e
        new GettingBitMapTask(imageView).execute(currentArticle.getaPictureURL());


        //Put the Description in
        TextView descriptionTextView = listLayout.findViewById(R.id.DescriptionLabel);

        descriptionTextView.setText(currentArticle.getaDescription());


        //Getting Comments
        TextView commentNumberTV = listLayout.findViewById(R.id.commentNumber);
        commentNumberTV.setText(currentArticle.getaCommentNumber());


        return listLayout;
    }

    @Nullable
    @Override
    public Article getItem(int position) {


        return super.getItem(position);
    }

    private class GettingBitMapTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public GettingBitMapTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


