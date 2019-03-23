package club.dev.mobile.ksu.clickermonkey3_android;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView dancingMonkey;
    private TextView clickCounterTV;
    private TextView countdownTimerTV;
    private TextView clickGoalTV;
    private AnimationDrawable dancingMonkeyAnimation;
    private int monkeyClicks;
    private int clickGoal = 10;
    private int timerLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickCounterTV = findViewById(R.id.click_counter);
        countdownTimerTV = findViewById(R.id.countdown_timer);
        clickGoalTV = findViewById(R.id.click_goal);
        dancingMonkey = findViewById(R.id.dancing_monkey);

        clickGoalTV.setText("Click goal = " + clickGoal);
        dancingMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonkeyClicked();
            }
        });

        startAnimation();
        startTimer(timerLength);

    }

    private void startAnimation() {
        dancingMonkeyAnimation = (AnimationDrawable) dancingMonkey.getBackground();
        dancingMonkeyAnimation.start();
    }

    private void startTimer(int seconds) {
        new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTimerTV.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTimerTV.setText("done!");
                onTimeOut();
            }
        }.start();
    }

    private void onMonkeyClicked() {
        monkeyClicks++;
        clickCounterTV.setText("Clicks = " + monkeyClicks);
    }

    private void onTimeOut() {
        dancingMonkeyAnimation.stop();
        dancingMonkey.setOnClickListener(null);
        countdownTimerTV.setText("OUT OF TIME!");
    }
}
