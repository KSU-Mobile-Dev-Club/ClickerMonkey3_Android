package club.dev.mobile.ksu.clickermonkey3_android;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.clickermonkey3_android.Repository;

public class ScoreboardActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    final List<Player> dataList = new ArrayList<Player>();
    int userScore = 5;
    String userName = "Test3";
    String userKey = "PlaceTest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        getChildren();

        }

    public void Add(String s, int i, String k)
    {
        Player user = new Player(s, i, k);
        String key = mDatabase.push().getKey();
        mDatabase.child("scores").child(key).setValue(user);
    }

    public void Delete(String k)
    {
        mDatabase.child("scores").child(k).removeValue();
    }


    public void getChildren()
    {
        mDatabase.child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren())
                {
                    Player player = postSnapshot.getValue(Player.class);
                    player.setKey(postSnapshot.getKey());
                    dataList.add(player);
                }
                mDatabase.child("scores").orderByChild("score");

                for(int i = 0; i < dataList.size(); i++)
                {
                    if(userScore > dataList.get(i).getScore());
                    {
                        Add(userName, userScore, userKey);
                        Delete(dataList.get(dataList.size() - 1).key);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
