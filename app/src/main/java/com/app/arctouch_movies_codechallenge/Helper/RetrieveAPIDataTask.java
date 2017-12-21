package com.app.arctouch_movies_codechallenge.Helper;

import android.os.AsyncTask;
import android.util.Log;

import com.app.arctouch_movies_codechallenge.Helper.MyApp;
import com.app.arctouch_movies_codechallenge.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.provider.Settings.Global.getString;

/**
 * Created by igorv on 11/12/2017.
 */

public class RetrieveAPIDataTask extends AsyncTask<String, Void, String>
{
    @Override
    protected String doInBackground(String... actions) {
        String apiURL  = MyApp.getAppContext().getString(R.string.apiURL);
        String apiKey  = MyApp.getAppContext().getString(R.string.apiKey);

        try {
            StringBuilder urlSB = new StringBuilder();
            urlSB.append(apiURL);
            urlSB.append(actions[0]);
            urlSB.append("?api_key=");
            urlSB.append(apiKey);
            if(actions.length > 1)
            {
                for (String action : actions) {
                    urlSB.append("&");
                    urlSB.append(action);
                }
            }

            URL url = new URL(urlSB.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("JSON", stringBuilder.toString());
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}
