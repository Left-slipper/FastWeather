package ry.geekbeains.fastweather.files.DataServise;

import android.app.Application;
import android.content.Intent;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

import ry.geekbeains.fastweather.MainActivity;
import ry.geekbeains.fastweather.R;


public class GetDtataServise {

    private static final String TAG = "GetDataServis";
    private final Handler handler;
    private final URL uri;

    public static final String EXTRA_TEMPERATURE = "ry.geekbeains.fastweather.ui.main.dataServes.GetDataServer.TEMPERATURE";
    public static final String EXTRA_PRESSURE = "ry.geekbeains.fastweather.ui.main.dataServes.GetDataServer.PRSSURE";
    public static final String EXTRA_WINDSPEED = "ry.geekbeains.fastweather.ui.main.dataServes.GetDataServer.WINDSPEED";


    public GetDtataServise(URL uri, Handler handler) {
       this.uri = uri;
       this.handler = handler;

       conectionServer();
   }

   public void conectionServer(){

       while (true){

           new Thread(new Runnable() {
               @Override
               public void run() {
                   HttpsURLConnection urlConnection = null;

                   try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);

                       BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                       String result = getLines(in);

                      Gson  gson = new Gson();
                      final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);


                      handler.post(new Runnable() {
                          @Override
                          public void run() {
                                displayWeather(weatherRequest);
                          }
                      });

                   }catch (Exception e){
                       Log.e(TAG, "FAIL CONNECTION");
                       e.printStackTrace();
                   }finally {
                       if(null != urlConnection){
                           urlConnection.disconnect();
                       }
                   }

               }
           }).start();
           try {
               Thread.sleep(350000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

   }

    private String getLines(BufferedReader in) {
       return in.lines().collect(Collectors.joining("\n"));
    }

    public void displayWeather(WeatherRequest weatherRequest)
    {
        String name = weatherRequest.getName();
        String temperature = String.format("%f2", weatherRequest.getMain().getTemp());
        String pressure = String.format("%d", weatherRequest.getMain().getPressure());
        String windSpeed = String.format("%d", weatherRequest.getWind().getSpeed());

        sendBrodcast(temperature, pressure, windSpeed);
    }
    private void sendBrodcast(String temperature, String pressure, String windSpeed) {
        Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION_CALCFINISHED);
        broadcastIntent.putExtra(EXTRA_TEMPERATURE, temperature);
        broadcastIntent.putExtra(EXTRA_PRESSURE, windSpeed);
        broadcastIntent.putExtra(EXTRA_WINDSPEED, pressure);
    }



}
