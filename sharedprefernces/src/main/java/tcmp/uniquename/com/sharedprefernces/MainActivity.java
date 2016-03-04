package tcmp.uniquename.com.sharedprefernces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        preferences.edit().
                putInt("intKey", 90).
                putString("name", "Izik").
                commit();

    }

    public void statrOtherActivity(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
