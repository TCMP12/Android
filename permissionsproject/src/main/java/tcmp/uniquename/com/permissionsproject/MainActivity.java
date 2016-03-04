package tcmp.uniquename.com.permissionsproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_PERM_STORAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("testPermissions", "activity.onCreate bundle = "+savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            Log.d("testPermissions", "permission denied");

//            startActivity(); start other activity to request permission
            //finish
            //return
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                Log.d("testPermissions", "permission show request");
                //show why?

            }
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQ_PERM_STORAGE);

        }

        Log.d("testPermissions", "end onCreate");
        setContentView(R.layout.activity_main);


    }


    @Override
    public void onRequestPer    missionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("testPermissions", "onREquest, grant = "+grantResults);
    }
}
