package com.armin.caloriemeter.adapters;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.armin.caloriemeter.R;
import com.armin.caloriemeter.util.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ResultsAdapter extends ArrayAdapter<String> {

    ArrayList<String> results;
    ArrayList<String> results2;
    Context context;
    private LayoutInflater layoutInflater;

    public ResultsAdapter(Context context, ArrayList<String> results, ArrayList<String> results2) {
        super(context, 0);
        this.results = results;
        this.results2 = results2;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder holder;
        String s = results.get(position);
        String url = results2.get(position);

        if(position < getCount()) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.result_row, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.result_text_view);
                holder.imageView = (ImageView) convertView.findViewById(R.id.result_image_view);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.result_image_view);
            holder.textView.setText(s);
            if (holder.hasImage == false) {
            new ImageDownloaderTask(holder).execute(url);
            }else {
                Log.e("error", "error");
            }
        }else {
            Log.e("error","error");
        }
//		NewsItem newsItem = (NewsItem) listData.get(position);

        return convertView;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ViewHolder> imageViewReference;

        public ImageDownloaderTask(ViewHolder viewHolder) {
            imageViewReference = new WeakReference<ViewHolder>(viewHolder);


        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ViewHolder viewHolder = imageViewReference.get();
                if (viewHolder != null) {
                    if (bitmap != null) {
                        viewHolder.imageView.setImageBitmap(bitmap);
                        viewHolder.hasImage = true;

                    } else {
                        Drawable placeholder = viewHolder.imageView.getContext().getResources().getDrawable(R.drawable.add_new_food);
                        viewHolder.imageView.setImageDrawable(placeholder);
        }
    }
}
}
        }

private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
        URL uri = new URL(url);
        urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
        int positionIndex;
        boolean hasImage = false;
    }
}
