package com.app.arctouch_movies_codechallenge.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.app.arctouch_movies_codechallenge.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by igorv on 11/12/2017.
 */

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap>
{
    private final WeakReference<ImageView> imageView;

    public DownloadImagesTask(ImageView imageView){
        this.imageView = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String apiURL  = MyApp.getAppContext().getString(R.string.imageURLBase);

        try {
            URL url = new URL(apiURL + urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream input = urlConnection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(input);
                return image;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR DownloadTask", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(Bitmap result){
        if (imageView != null) {
            ImageView img = imageView.get();
                        if (img != null) {
                if (result != null) {
                    img.setImageBitmap(result);
                }
            }
        }
    }
}
