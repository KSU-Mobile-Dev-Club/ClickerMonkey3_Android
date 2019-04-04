package club.dev.mobile.ksu.clickermonkey3_android;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT  = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
