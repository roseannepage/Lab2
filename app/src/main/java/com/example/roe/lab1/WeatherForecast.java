package com.example.roe.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar progBar;
    TextView currentT, minT, maxT;
    ImageView imgV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        maxT = (TextView)findViewById(R.id.maximumTemp);
        minT = (TextView)findViewById(R.id.minimumTemp);
        currentT = (TextView)findViewById(R.id.currentTemp);
        imgV = (ImageView)findViewById(R.id.weatherImg) ;
        progBar = (ProgressBar)findViewById(R.id.progressBar);
        progBar.setMax(100);
        progBar.setVisibility(View.VISIBLE);

        //new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
        new ForecastQuery().execute();
    }


    public class ForecastQuery  extends AsyncTask<String, Integer, String>{

        String current, min, max;
        Bitmap weatherImg;
        String bitmapTemp;
        protected String doInBackground(String ... args)
        {



            try{

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000); //in milliseconds
                urlConnection.setConnectTimeout(15000); //in millisenconds
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);


                urlConnection.connect();
                InputStream istream = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(istream, null);
                boolean finished = false;
                int type = xpp.getEventType();

                while(type != XmlPullParser.END_DOCUMENT) {

                    switch (type) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            finished = true;
                            break;
                        case XmlPullParser.START_TAG:

                            String name = xpp.getName();
                            if (name.equals("temperature")) {
                                current = xpp.getAttributeValue(null,"value");
                                publishProgress(25);
                                min = xpp.getAttributeValue(null,"min");
                                publishProgress(50);
                                max = xpp.getAttributeValue(null,"max");
                                publishProgress(75);

                            }

                            if (name.equals("weather")) {
                                bitmapTemp = xpp.getAttributeValue(null,"icon");
                                publishProgress(100);


                            }

                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    type = xpp.next(); //advances to next xml event
                }
                urlConnection.disconnect();
                if(fileExist(bitmapTemp + ".png")){
                    File file = getBaseContext().getFileStreamPath(bitmapTemp + ".png");
                    FileInputStream fis = new FileInputStream(file);
                    weatherImg = BitmapFactory.decodeStream(fis);
                }else{
                     URL imgURL = new URL("http://openweathermap.org/img/w/" + bitmapTemp + ".png");
                    urlConnection = (HttpURLConnection) imgURL.openConnection();
                    urlConnection.connect();
                    istream = urlConnection.getInputStream();
                    weatherImg = BitmapFactory.decodeStream(istream);

                    FileOutputStream fos = openFileOutput(bitmapTemp + ".png", Context.MODE_PRIVATE);
                    weatherImg.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    urlConnection.disconnect();
                }
            }
            catch(Exception e)
            {
                Log.e("XML PARSING", e.getMessage());
            }

            return null;
        }





        @Override
        protected void onProgressUpdate(Integer ...value)
        {
            progBar.setVisibility(View.VISIBLE);
            progBar.setProgress(value[0]);

        }

        @Override
        protected void onPostExecute(String srgs){
            progBar.setVisibility(View.INVISIBLE);
            currentT.setText("Current Temp: " + current + "ºC");
            minT.setText("Minimum: " + min + "ºC");
            maxT.setText("Maximum: " + max  + "ºC");
            imgV.setImageBitmap(weatherImg);
        }

        public Boolean fileExist(String name){
            File file = getBaseContext().getFileStreamPath(name);
            return file.exists();
        }
    }

}
