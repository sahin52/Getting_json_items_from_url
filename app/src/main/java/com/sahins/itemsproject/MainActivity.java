package com.sahins.itemsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static TextView trial;
    public static int jsonlength;
    String json ="";
    public static ListView list;
    public static ArrayList<HashMap<String, String >> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemlist = new ArrayList<>();
        //Toast.makeText(this,"app entered",Toast.LENGTH_SHORT).show();
        list  = (ListView) findViewById(R.id.liste);
        trial = (TextView) findViewById(R.id.trial); //hello isimli texti trial diye almisim, duzeltildi
        GetDataBackground getDataBackground = new GetDataBackground();
        getDataBackground.execute(); //Burada her seyi yapmam lazim

        // we got the json format as text;
        //Toast.makeText(this,"trying",Toast.LENGTH_SHORT).show();


        //new DownloadImageFromInternet((ImageView) findViewById(R.id.trialimage))
        //      .execute("https://storage.googleapis.com/anvato-sample-dataset-nl-au-s1/life-1/image-MPK9uyivbMPMjcwVW-rCJA.jpg");
        //Toast.makeText(this,"jsonlength",Toast.LENGTH_SHORT).show();
        //json = json + String.valueOf(jsonlength);
        //Toast.makeText(this,json, Toast.LENGTH_SHORT).show();


    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageview;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageview = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                //Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageview.setImageBitmap(result);
        }
    }

    public class GetDataBackground extends AsyncTask<String, String, String> {
        BufferedReader bufferedReader = null;
        String result = "";
        String jsontrial="";
        int length;
        int len;
        JSONObject bject;
        JSONArray items;


        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("https://storage.googleapis.com/anvato-sample-dataset-nl-au-s1/life-1/data.json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream is = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is));
                String getline = "";
                while(getline != null){

                    getline = bufferedReader.readLine();
                    if(getline != null)
                        result = result + getline;
                }
                JSONObject bject = new JSONObject(result); //Tum verileri jsonobjecte donusturduk

                JSONArray items =  (JSONArray) bject.get("items"); //itemleri aldik

                length = items.length();

                for (int i = 0; i < items.length(); i++) {
                    JSONObject j = items.getJSONObject(i); //bir itemi aldik
                    String title =  j.getString("title");
                    HashMap<String, String> oneitem = new HashMap<>();
                    oneitem.put("title",title);
                    itemlist.add(oneitem);
                    
                }

                //JSONObject oneitem = (JSONObject) items.get(0);
                //jsontrial = "" + oneitem.get("title");
                //burada liste isini hallet


            }
            catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //MainActivity.trial.setText(this.jsontrial);
            //MainActivity.jsonlength = length;
            //Toast.makeText(,String.valueOf(length),Toast.LENGTH_SHORT).show();
            //ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(src));
            //MainActivity.list.setAdapter(new CustomListAdapte( MainActivity.getContext(), srcList));

            ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, itemlist, R.layout.list_view_layout, new String[]{"title"},new int[]{R.id.text});

            MainActivity.list.setAdapter(listAdapter);



        }
    }
}
