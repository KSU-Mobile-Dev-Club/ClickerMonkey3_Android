package club.dev.mobile.ksu.clickermonkey3_android;

import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdadpter(ArrayList<Player> players){

    }

    public View getView(int position, View childView, ViewGroup parent){

        if (childView == null) {
            childView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        TextView player = (TextView) childView.findViewById(R.id.player);
        TextView score = (TextView) childView.findViewById(R.id.score);

    }

}
