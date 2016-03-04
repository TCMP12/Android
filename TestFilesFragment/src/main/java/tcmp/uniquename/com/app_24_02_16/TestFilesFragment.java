package tcmp.uniquename.com.app_24_02_16;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tcmp on 24/02/2016.
 */
public class TestFilesFragment extends Fragment implements View.OnClickListener {


    public TestFilesFragment() {
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootFragment = inflater.inflate(R.layout.fragment_a, null);
        rootFragment.findViewById(R.id.button).setOnClickListener(this);
        rootFragment.findViewById(R.id.button2).setOnClickListener(this);
        rootFragment.findViewById(R.id.button3).setOnClickListener(this);
        rootFragment.findViewById(R.id.button4).setOnClickListener(this);
        return rootFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                createInternal();
                break;
            case R.id.button2:
                createExternalFile();
                break;
            case R.id.button3:
                createExternalData();
                break;
            case R.id.button4:
                copyFile("text.txt");
                break;
        }

    }

    private void copyFile(String s) {
        try {
            InputStream inputStream =
                    new FileInputStream(new File(Environment.
                            getExternalStorageDirectory(), s));
            OutputStream outputStream = new FileOutputStream
                    (new File(getContext().getFilesDir(), s),true);

            byte[] buffer = new byte[4];
            int counter =0;

            while ((counter = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, counter);
//                new String(buffer, 0, counter);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }













    private void createExternalFile() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            createExternalFileDontAsk();
        }
    }

    private void createExternalFileDontAsk() {
        File root = Environment.getExternalStorageDirectory();
        //sdcard
        File folder = new File(root ,"NewFolderrrrrr");
        boolean created = folder.mkdir();
        File f = new File(folder, "myjkhjkile.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isReadable(File file){
        return EnvironmentCompat.getStorageState(file)
                .equals(Environment.MEDIA_MOUNTED_READ_ONLY)| isWrite();
    }

    private static boolean isWrite(){
        return EnvironmentCompat.getStorageState(Environment.getExternalStorageDirectory()).equals(Environment.MEDIA_MOUNTED);
    }

    private void createExternalData() {

//        if(Build.VERSION_CODES.LOLLIPOP > BuildConfig.VERSION_CODE){
//
//        }

        File dataRoot = getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        //sdcard/Android/data/<pkg name>/files
        File folder = new File(dataRoot ,"New Folder");
        boolean created = folder.mkdir();
        File f = new File(folder, "myile.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createInternal() {
        //Internal Storage
        File izikRoot = getContext().getFilesDir();//data/data<pkg name>/files
        File folder = new File(izikRoot ,"New Folder");
        boolean created = folder.mkdir();
        File f = new File(folder, "myFile.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1&& grantResults!=null&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
            createExternalFileDontAsk();
        }
    }


}
