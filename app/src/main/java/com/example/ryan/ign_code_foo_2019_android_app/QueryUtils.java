package com.example.ryan.ign_code_foo_2019_android_app;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {
    //Got Base code from the Quake App through Udacity, then I put my own spin on it.
    private String LOG_TAG = "QueryUtils";
    private String whichFragment;


    // Perform HTTP request to the URL and receive a JSON response back
    public List<Article> getDataFromInternet(URL url, String fragmentCaller) {
        Log.v(LOG_TAG, "Started the getDataFromInternet Method");
        whichFragment = fragmentCaller;
        String jsonResponse = "";
        try {
            Log.v(LOG_TAG, "Making Http Request");
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // TODO Handle the IOException
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        Log.v(LOG_TAG, "Extractring the jsonObject");
        List<Article> tempList = extractFeatureFromJson(jsonResponse);

        //Example: https://ign-apis.herokuapp.com/comments?ids=3de45473c5662f25453551a2e1cb4e6e,63a71f01cca67c9bbf5e7b6f091d551d
        //Build Base UrL string
        String baseCommentURL = "https://ign-apis.herokuapp.com/comments";
        String commentIDs = "";
        //Put the content Id's in array
        for (int i = 0; i < tempList.size(); i++) {
            Article tempArticle = tempList.get(i);
            if (i == (tempList.size() - 1)) {
                commentIDs += tempArticle.getaContentID();
            } else {
                commentIDs += tempArticle.getaContentID() + ',';

            }
        }
        // Parsing the basic String URL
        Uri uriBase = Uri.parse(baseCommentURL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = uriBase.buildUpon();


        // The URI that has my results: https://ign-apis.herokuapp.com/content?startIndex=8/u0026count=5

        //Build the URI appropriately.
        uriBuilder.appendQueryParameter("ids", commentIDs);

        String builderURI = uriBuilder.toString();

        URL commentURL = createUrl(builderURI);

        String jsonResponse2 = "";
        try {
            Log.v(LOG_TAG, "Making Http Request");
            jsonResponse2 = makeHttpRequest(commentURL);
        } catch (IOException e) {
            // TODO Handle the IOException
        }

        List<String> commentNumbers = extractFeatureFromJson2(jsonResponse2);

        //Put the commentNumbers within their articles
        for (int k = 0; k < tempList.size(); k++) {
            Article presentArticle = tempList.get(k);
            presentArticle.setaCommentNumber(commentNumbers.get(k));

        }


        return tempList;

    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Return an {@link ArrayList< Article > object by parsing out information
     */
    private List<Article> extractFeatureFromJson(String ArticleJSON) {
        List<Article> theArticles = new ArrayList<>();
        try {
            Log.v(LOG_TAG, "Starting Extracting JSON!");

            JSONObject baseJsonResponse = new JSONObject(ArticleJSON);
            JSONArray dataArray = baseJsonResponse.getJSONArray("data");
            Log.v(LOG_TAG, "Made it to dataArray");

            for (int j = 0; j < dataArray.length(); j++) {
                JSONObject IGNObject = dataArray.getJSONObject(j);

                // Now that we have an IGN Object, we have to check if it's an article or video
                String typeOfIGNObject = IGNObject.getString("contentType");

                if (whichFragment.equals("ArticleFragment") && typeOfIGNObject.equals("article")) {
                    String ContentID = IGNObject.getString("contentId");


                    // Have to get the thumbnail array, the first object, and get the url string fro the picture
                    JSONArray thumbnailArray = IGNObject.getJSONArray("thumbnails");
                    JSONObject pictureObject = thumbnailArray.getJSONObject(0);
                    String Picture = pictureObject.getString("url");

                    // Have to get description, title, and Posted Date
                    JSONObject metadataObject = IGNObject.getJSONObject("metadata");

                    String description = metadataObject.getString("description");

                    String title = metadataObject.getString("headline");

                    String URL = metadataObject.getString("slug");

                    String Date1 = metadataObject.getString("publishDate");

                    //Formatting Data...use the official Website for SimpleDateFormat
                    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date newDate = spf.parse(Date1);
                    spf = new SimpleDateFormat("mm");
                    Date1 = spf.format(newDate);

                    //Formatting Data...use the official Website for SimpleDateFormat
                    SimpleDateFormat spf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date newDate2 = spf.parse(Date1);
                    spf = new SimpleDateFormat("/yyyy/MM/dd/");
                    String uRLDATE = spf.format(newDate);




                    theArticles.add(new Article(URL, title, description, Date1, uRLDATE, Picture, ContentID));


                } else if (whichFragment.equals("VideoFragment") && typeOfIGNObject.equals("video")) {
                    String ContentID = IGNObject.getString("contentId");


                    // Have to get the thumbnail array, the first object, and get the url string fro the picture
                    JSONArray thumbnailArray = IGNObject.getJSONArray("thumbnails");
                    JSONObject pictureObject = thumbnailArray.getJSONObject(0);
                    String Picture = pictureObject.getString("url");

                    // Have to get description, title, and Posted Date
                    JSONObject metadataObject = IGNObject.getJSONObject("metadata");

                    String description = metadataObject.getString("description");

                    String title = metadataObject.getString("title");

                    String URL = metadataObject.getString("slug");

                    String Date1 = metadataObject.getString("publishDate");

                    //Formatting Data...use the official Website for SimpleDateFormat
                    SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date newDate = spf.parse(Date1);
                    spf = new SimpleDateFormat("mm");
                    Date1 = spf.format(newDate);

                    //Formatting Data...use the official Website for SimpleDateFormat
                    SimpleDateFormat spf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    Date newDate2 = spf.parse(Date1);
                    spf = new SimpleDateFormat("/yyyy/MM/dd/");
                    String uRLDATE = spf.format(newDate);


                    //Put the article in the List:String URL, String Title,  String Date, String Picture, String ID

                    //

                    theArticles.add(new Article(URL, title, description, Date1, uRLDATE, Picture, ContentID));


                } else {
                    //Do nothing

                }


            }
            Log.v(LOG_TAG, "Done getting the data." + theArticles);


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        } catch (ParseException e) {
            Log.v(LOG_TAG, "Problems with formatting the date");
            e.printStackTrace();
        }
        return theArticles;
    }

    private List<String> extractFeatureFromJson2(String ArticleJSON) {
        List<String> theCommentNumbers = new ArrayList<>();
        try {
            Log.v(LOG_TAG, "Starting Extracting JSON!");

            JSONObject baseJsonResponse = new JSONObject(ArticleJSON);
            JSONArray dataArray = baseJsonResponse.getJSONArray("content");
            Log.v(LOG_TAG, "Made it to Array");

            for (int j = 0; j < dataArray.length(); j++) {
                JSONObject IGNObject = dataArray.getJSONObject(j);

                // Now that we have an IGN Object, we have to check if it's an article or video
                String commentNumber = IGNObject.getString("count");
                theCommentNumbers.add(commentNumber);


            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return theCommentNumbers;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        if (jsonResponse == "" || jsonResponse == null) {
            Log.v(LOG_TAG, "NO JSON");
        }
        Log.v(LOG_TAG, "JSON is something I guess");
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
