package tcmp.uniquename.com.sharedprefernces;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        int i = preferences.getInt("intKey", -1);
        String name = preferences.getString("name", null);
        String test = preferences.getString("test", "no key");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("name: "+name);
        tv.append("\ntest: "+test);
        tv.append("\nintKey: "+i);
    }
}
