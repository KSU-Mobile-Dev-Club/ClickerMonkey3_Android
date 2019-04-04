package club.dev.mobile.ksu.clickermonkey3_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {

    PlayerAdapter adapter;
    ArrayList<Player> playerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        ListView scoreBoardRankingListView = findViewById(R.id.scoreListView);

        // Populate player list here

        adapter = new PlayerAdapter(this, playerList);
        scoreBoardRankingListView.setAdapter(adapter);
    }
}
