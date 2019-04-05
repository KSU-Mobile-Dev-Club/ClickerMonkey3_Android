package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void playGame(View view) {
        Intent playIntent = new Intent(this, ClickerActivity.class);
        startActivity(playIntent);
    }

    public void viewScoreboard(View view) {
        Intent viewScoreboardIntent = new Intent(this, ScoreboardActivity.class);
        startActivity(viewScoreboardIntent);
    }

    public void viewAttributions(View view) {
        Intent viewAttributionsIntent = new Intent(this, AttributesActivity.class);
        startActivity(viewAttributionsIntent);
    }
}
