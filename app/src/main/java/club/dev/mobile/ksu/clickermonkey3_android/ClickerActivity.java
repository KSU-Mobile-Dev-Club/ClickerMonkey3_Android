package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClickerActivity extends AppCompatActivity {
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
        beatRoundEffect = MediaPlayer.create(this, R.raw.sample2);
        lostEffect = MediaPlayer.create(this, R.raw.sample3);

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
        clickGoalTV.setText("Click Goal: " + level * 10);
        levelTV.setText("Level " + level);
        monkeyClicks = 0;
        clickCounterTV.setText("Monkey Clicks: " + monkeyClicks);

        dancingMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonkeyClicked();
            }
        });

        startAnimation();
        startTimer();
        mTimerView.start(TIMER_LENGTH);
    }

    private void startAnimation() {
        dancingMonkey.startAnimation(dancingMonkeyAnimation);
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
        monkeyClicks++;
        clickCounterTV.setText("Monkey Clicks: " + monkeyClicks);
    }

    private void onTimeOut() {
        dancingMonkeyAnimation.cancel();
        mTimerView.stop();
        dancingMonkey.setOnClickListener(null);
        if (monkeyClicks < (10*level)) {
            onLose();
        }
        else {
            onWin();
        }
    }

    private void onLose() {
        lostEffect.start();
        checkHighScore();
        new AlertDialog.Builder(this)
                .setTitle("You lose!")
                .setMessage("Too bad...Do you want to play again?")
                .setPositiveButton("Heck yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                })
                .show();
    }

    private void restart() {
        level = 1;
        dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey));
        startRound();
    }

    private void onWin() {
        beatRoundEffect.start();
        level++;
        if (level == 2) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey2));
        }
        else if (level == 3) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey3));
        }
        else if (level == 4) {
            dancingMonkey.setImageDrawable(getResources().getDrawable(R.drawable.monkey4));
        }
        startRound();
    }

    private void checkHighScore() {
        //TODO: check to see if they get to go on the scoreboard
    }
}
