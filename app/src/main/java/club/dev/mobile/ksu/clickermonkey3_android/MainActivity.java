package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
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
    private TextView levelTV;
    private AnimationDrawable dancingMonkeyAnimation;
    private int level = 1;
    private int monkeyClicks;
    private static final int TIMER_LENGTH = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("CLICKER MONKEY 3");

        levelTV = findViewById(R.id.level);
        clickCounterTV = findViewById(R.id.click_counter);
        countdownTimerTV = findViewById(R.id.countdown_timer);
        clickGoalTV = findViewById(R.id.click_goal);
        dancingMonkey = findViewById(R.id.dancing_monkey);

        startRound();
    }

    private void startRound() {
        clickGoalTV.setText("Click goal = " + level * 10);
        levelTV.setText("Level = " + level);
        monkeyClicks = 0;
        clickCounterTV.setText("Monkey clicks = " + monkeyClicks);

        dancingMonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMonkeyClicked();
            }
        });

        startAnimation();
        startTimer();
    }

    private void startAnimation() {
        dancingMonkeyAnimation = (AnimationDrawable) dancingMonkey.getBackground();
        dancingMonkeyAnimation.start();
    }

    private void startTimer() {
        new CountDownTimer(1000 * TIMER_LENGTH, 1000) {

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
        clickCounterTV.setText("Monkey Clicks = " + monkeyClicks);
    }

    private void onTimeOut() {
        dancingMonkeyAnimation.stop();
        dancingMonkey.setOnClickListener(null);
        if (monkeyClicks < (10*level)) {
            onLose();
        }
        else {
            onWin();
        }
    }

    private void onLose() {
        countdownTimerTV.setText("OUT OF TIME!");
        checkHighScore();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("You lose!")
                .setMessage("Too bad...Do you want to play again?")
                .setPositiveButton("Heck yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restart();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void restart() {
        level = 1;
        startRound();
    }

    private void onWin() {
        level++;
        getMonkeyAccessory();
        startRound();
    }

    private void getMonkeyAccessory() {

    }

    private void checkHighScore() {

    }
}
