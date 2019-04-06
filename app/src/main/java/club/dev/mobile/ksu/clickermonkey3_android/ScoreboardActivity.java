package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    final ArrayList<Player> dataList = new ArrayList<>();
    PlayerAdapter adapter;
    ProgressBar progressBar;
    public static String USER_SCORE = "User Score";
    private int userScore;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        userScore = getIntent().getIntExtra(USER_SCORE, 0);

        ListView scoreBoardRankingListView = findViewById(R.id.scoreListView);
        progressBar = findViewById(R.id.progress_bar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new PlayerAdapter(this, new ArrayList<>(dataList));
        scoreBoardRankingListView.setAdapter(adapter);
        getScores();
    }

    public void getScores()
    {
        mDatabase.child("scores").orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    Player player = postSnapshot.getValue(Player.class);
                    player.setKey(postSnapshot.getKey());
                    dataList.add(player);
                }

                Collections.reverse(dataList);

                //if the user score is greater than 0, then this activity was entered from the
                //scoreboard activity and we need to see if the current player can add themselves
                //to the leaderboard (otherwise we are just viewing the scoreboard)
                if (userScore > 0) {
                    checkIfHighScore();
                }

                adapter.setPlayers(dataList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateScores() {
        dataList.clear();
        mDatabase.child("scores").orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    Player player = postSnapshot.getValue(Player.class);
                    player.setKey(postSnapshot.getKey());
                    dataList.add(player);
                }

                Collections.reverse(dataList);

                adapter.setPlayers(dataList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkIfHighScore() {
        //if there are less than players on the scoreboard, add this player
        if (dataList.size() < 5) {
            AddCurrentPlayer();
        }

        //otherwise, see if our score beats any of the high scores
        else {
            for(int i = 0; i < dataList.size(); i++)
            {
                if(userScore > dataList.get(i).getScore())
                {
                    AddCurrentPlayer();
                    Delete(dataList.get(dataList.size() - 1).key);
                    break;
                }
            }
        }
    }

    public void AddCurrentPlayer()
    {
        final View view = LayoutInflater.from(this).inflate(R.layout.player_name_dialog, null);
        final EditText nameET = view.findViewById(R.id.player_name_edittext);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(R.string.message_high_score)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        playerName = nameET.getText().toString();
                        Player user = new Player(playerName, userScore);
                        String key = mDatabase.push().getKey();
                        mDatabase.child("scores").child(key).setValue(user);
                        progressBar.setVisibility(View.VISIBLE);
                        updateScores();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                if (name.equals("")) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    return;
                }
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }
        });
    }


    public void Delete(String k)
    {
        mDatabase.child("scores").child(k).removeValue();
    }

    public void backButtonClicked(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
