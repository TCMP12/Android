package izik.manager.travles.com.filesmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FileInfo.init(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_s_abc:
                ((ExplorerFragment) getSupportFragmentManager().
                        getFragments().get(0)).getManager().sortList(FileInfo.SORT_AB);
                return true;
            case R.id.m_s_date:
                ((ExplorerFragment) getSupportFragmentManager().
                        getFragments().get(0)).getManager().sortList(FileInfo.SORT_DATE);
                return true;
            case R.id.m_s_size:
                ((ExplorerFragment) getSupportFragmentManager().
                        getFragments().get(0)).getManager().sortList(FileInfo.SORT_SIZE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments() != null) {
            if (getSupportFragmentManager().getFragments().get(0) instanceof ExplorerFragment)

                if (((ExplorerFragment) getSupportFragmentManager().getFragments().get(0)).onBackPressed())
                    return;

        }
        super.onBackPressed();
    }
}