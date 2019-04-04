package club.dev.mobile.ksu.clickermonkey3_android;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private DatabaseReference mDatabase;
    final List<Player> dataList = new ArrayList<Player>();
    public Repository()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                    dataList.add(player);
                }
                mDatabase.child("scores").orderByChild("score");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
