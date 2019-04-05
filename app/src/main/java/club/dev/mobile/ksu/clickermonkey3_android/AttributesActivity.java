package club.dev.mobile.ksu.clickermonkey3_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AttributesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);
    }

    public void backButtonClicked(View view) {
        onBackPressed();
    }
}
