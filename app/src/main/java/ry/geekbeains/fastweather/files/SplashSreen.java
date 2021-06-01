package ry.geekbeains.fastweather.files;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import ry.geekbeains.fastweather.MainActivity;
import ry.geekbeains.fastweather.R;

public class SplashSreen extends Activity {

    private final int SPLASH_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashSreen.this, MainActivity.class);
                SplashSreen.this.startActivity(mainIntent);
                SplashSreen.this.finish();
            }
        }, SPLASH_LENGHT);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "Хорошего дня", Toast.LENGTH_SHORT).show();
        Log.d("SplashScreen", "onBackPressed");
        System.exit(0);
    }
}
