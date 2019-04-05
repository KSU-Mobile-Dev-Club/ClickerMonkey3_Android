package club.dev.mobile.ksu.clickermonkey3_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {
    ArrayList<Player> players = new ArrayList<>();

    public PlayerAdapter(Context context, ArrayList<Player> players){
        super(context, 0);
    }

    public View getView(int position, View childView, ViewGroup parent){

        if (childView == null) {
            childView = LayoutInflater.from(getContext()).inflate(R.layout.playeritem, parent, false);
        }

        TextView player = childView.findViewById(R.id.player);
        TextView score = childView.findViewById(R.id.score);

        player.setText(players.get(position).getName());
        score.setText(Integer.toString(players.get(position).getScore()));

        return childView;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }
}
