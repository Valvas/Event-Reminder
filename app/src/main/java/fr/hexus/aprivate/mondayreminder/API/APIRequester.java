package fr.hexus.aprivate.mondayreminder.API;

import android.app.VoiceInteractor;
import android.content.Context;
import android.net.http.RequestQueue;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.client.json.Json;
import com.google.gson.JsonObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nicolas on 19/12/2017.
 */

abstract public class APIRequester {

    public static String baseURL = null;

    /**
     * Build a HTTP Request and get the response of the API
     * @param URL API Route URL
     * @param content List for body HTTP Request
     */
    public void readFromUrl(String URL, final JSONObject content, String methodRequest, Context caller, final APICallback callback) throws Exception{
        try {
            com.android.volley.RequestQueue queue = Volley.newRequestQueue(caller);

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, content,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("API", error.getMessage());
                    }
                }
            );

            queue.add(request);
            queue.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Build a list for body HTTP Request
     * Unsupported by the API
     * @param listArguments If null create a new List, list of body HTTP Request data
     * @param contentName Name of body content
     * @param contentPOST Data of body content
     */
    public List<NameValuePair> buildPOSTList(List<NameValuePair> listArguments, String contentName,String contentPOST){
        if (listArguments == null && contentPOST == null) return null;
        if (contentPOST == null || contentName == null) return listArguments;
        if (listArguments == null) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(contentName, contentPOST));
            return params;
        }
        listArguments.add(new BasicNameValuePair(contentName, contentPOST));
        return listArguments;
    }

    /**
     * Transform a String to Date Object
     * @param date If null create a new List, list of body HTTP Request data
     */
    public java.util.Date getDateFrommString(String date) throws Exception{
        DateFormat format = new SimpleDateFormat("yyyy-M-dd", Locale.FRANCE);
        return format.parse(date.substring(0, 10));
    }

    /**
     * Formatting body of HTTP Request
     * Unsupported by the API
     * @param params List of body content
     */
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
