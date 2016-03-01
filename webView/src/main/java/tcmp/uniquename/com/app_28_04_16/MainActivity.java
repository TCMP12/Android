package tcmp.uniquename.com.app_28_04_16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().
                    add(R.id.container, new MyWebFragment()).commit();


        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
