package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickerActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_REQUEST = 1000;
    private ImageView dancingMonkey;
    private TextView clickCounterTV;
    private TextView clickGoalTV;
    private TextView levelTV;
    private Animation dancingMonkeyAnimation;
    private int level = 1;
    private int monkeyClicks;
    private static final int TIMER_LENGTH = 11;
    private CountDownTimer mTimer;
    private TimerView mTimerView;
    private MediaPlayer beatRoundEffect;
    private MediaPlayer lostEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        levelTV = findViewById(R.id.level);
        clickCounterTV = findViewById(R.id.click_counter);
        clickGoalTV = findViewById(R.id.click_goal);
        dancingMonkey = findViewById(R.id.dancing_monkey);
        dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey));
        dancingMonkeyAnimation = AnimationUtils.loadAnimation(this, R.anim.dancing_monkey);
        mTimerView = findViewById(R.id.timer);
        beatRoundEffect = MediaPlayer.create(this, R.raw.clickgoal);
        lostEffect = MediaPlayer.create(this, R.raw.loss);

        startRound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private void startRound() {
        setMonkeyImage();
        clickGoalTV.setText(getApplicationContext().getString(R.string.click_goal, Integer.toString(level * 10)));
        levelTV.setText(getApplicationContext().getString(R.string.level, Integer.toString(level)));
        monkeyClicks = 0;
        clickCounterTV.setText(getApplicationContext().getString(R.string.monkey_clicks, Integer.toString(monkeyClicks)));
        clickCounterTV.setTextColor(getResources().getColor(R.color.colorAccent));
        dancingMonkey.startAnimation(dancingMonkeyAnimation);
        dancingMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonkeyClicked();
            }
        });
        startTimer();
        mTimerView.start(TIMER_LENGTH);
    }

    private void setMonkeyImage() {
        if (level == 2) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey2));
        }
        else if (level == 3) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey3));
        }
        else if (level == 4) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey4));
        }
    }

    private void startTimer() {
        mTimer = new CountDownTimer(1000 * TIMER_LENGTH, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                onTimeOut();
            }
        }.start();
    }

    private void onMonkeyClicked() {
        if (monkeyClicks < 10 * level) {
            monkeyClicks++;
            clickCounterTV.setText(
                    getApplicationContext().getString(R.string.monkey_clicks, Integer.toString(monkeyClicks)));
            if (monkeyClicks == 10 * level) {
                level++;
                clickCounterTV.setTextColor(getResources().getColor(R.color.colorPrimary));
                onWin();
            }
        }

    }

    private void onTimeOut() {
        dancingMonkeyAnimation.cancel();
        mTimerView.stop();
        dancingMonkey.setOnClickListener(null);
        onLose();
    }

    private void onWin() {
        beatRoundEffect.start();
        mTimer.cancel();
        mTimerView.stop();
        showSplashScreen();
    }

    private void showSplashScreen() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivityForResult(intent, SPLASH_SCREEN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPLASH_SCREEN_REQUEST) {
            startRound();
        }
    }

    private void onLose() {
        lostEffect.start();
        Intent intent = new Intent(this, ScoreboardActivity.class);
        intent.putExtra(ScoreboardActivity.USER_SCORE, level - 1);
        startActivity(intent);
    }
}
