package tcmp.uniquename.com.mygrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView mGrid = (GridView) findViewById(R.id.gridView);
        mGrid.setAdapter(new MyAdapter(this));
    }
}
