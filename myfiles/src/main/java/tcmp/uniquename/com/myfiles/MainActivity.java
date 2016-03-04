package tcmp.uniquename.com.myfiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TestFiles";
    File externalRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if denied
        if(PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            ActivityCompat.requestPermissions(this
            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }else{ //allow
            loadFiles();
        }
    }

    private void loadFiles() {
        externalRoot = Environment.getExternalStorageDirectory();
        //sdcrad/emulate
        Log.d(TAG, "externalRoot path: "+externalRoot.getPath());
        Log.d(TAG, "externalRoot length: "+externalRoot.length());

        File newFolder = new File(externalRoot, "NewMyFolder");
        if(! newFolder.exists()){
            newFolder.mkdir();
        }
        Log.d(TAG, "newFolder exists: "+newFolder.exists());
        File newFile = new File(newFolder.getPath()+"/newFile.txt");

        if(! newFile.exists())
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        ListView mListView = (ListView) findViewById(R.id.listView);
        String[] names = externalRoot.list();
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults != null && grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadFiles();
                }
            }
        }
    }
}
