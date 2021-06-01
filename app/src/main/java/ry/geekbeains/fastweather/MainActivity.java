package ry.geekbeains.fastweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import ry.geekbeains.fastweather.files.DataServise.GetDtataServise;
// добавил проект
// не хочет добавляться
// больше коментов для ясности
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String BROADCAST_ACTION_CALCFINISHED = "ry.geekbeains.fastweather.files.DataServise.GetDataServer";


    private EditText city;
    private TextView temperature;
    private TextView pressure;
    private TextView windSpeed;
    private ImageButton searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.home);
        getSupportActionBar().hide();

        if (savedInstanceState == null){
            Toast.makeText(getApplicationContext(), "Доброе время суток", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "onCreated");
        }

        getinfo();
    }

    private void init(){
        city = findViewById(R.id.inCity);
        temperature = findViewById(R.id.gradus);
        pressure = findViewById(R.id.pressureView);
        windSpeed = findViewById(R.id.speedWinterView);
        searchButton = findViewById(R.id.searchCity);
        searchButton.setOnClickListener(clickListener);
    }

    private void getinfo(){
        try{
            final String url = "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid=" + BuildConfig.WEATHER_API_KEY;

            final URL  uri = new URL(url);
            final Handler handler = new Handler();
            new GetDtataServise(uri, handler);

        }catch (MalformedURLException e){
            Log.e(TAG, "FAIL URI", e);
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getinfo();
        }
    };

    public void SetInfo(){
        city.setText(weatherRequest.getName());
        temperature.setText(String.format("%f2", weatherRequest.getMain().getTemp()));
        pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
        windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Хорошего дня", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    }



}
