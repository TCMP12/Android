package teach.com.pazzle;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView mGridView = (GridView) findViewById(R.id.gridView);
        CustomAdapter adapter = new CustomAdapter(this);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(adapter);
    }

}
