package lk.sliit.se.espnsports.core;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dinukshakandasamanage on 3/25/18.
 */

public class Executor extends AsyncTask<Integer, Void, String> {

    private String url;
    private Callback cb;

    public Executor(String url, Callback cb) {
        this.url = url;
        this.cb = cb;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String result = null;

        try{
            URL url = new URL(this.url +
                    (integers == null || integers.length == 0 ? "" : "/" + integers[0]));
            result = getUrlConnectionResult(url);
        } catch (IOException e){

        }
        return result;
    }

    private String getUrlConnectionResult(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream stream = conn.getInputStream();
        InputStreamReader reader = new InputStreamReader(stream);

        int MAX_READ_SIZE = 1000000;
        char[] buffer = new char[MAX_READ_SIZE];
        int readSize;
        StringBuffer buf = new StringBuffer();

        while (((readSize = reader.read(buffer)) != -1) && MAX_READ_SIZE > 0) {
            if (readSize > MAX_READ_SIZE) {
                readSize = MAX_READ_SIZE;
            }
            buf.append(buffer, 0, readSize);
            MAX_READ_SIZE -= readSize;
        }
        return buf.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            this.cb.onCallbackCompleted(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
