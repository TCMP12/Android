package izik.manager.travles.com.filesmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class NotepadActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);


        if(getIntent().getData() != null){
            EditText editText = (EditText) findViewById(R.id.editText);
            readText(getIntent().getData().getPath(), editText);
        }else
            finish();
    }

    private void readText(final String location, final EditText editText) {
        new AsyncTask<Void,Void, StringBuilder>(){

            @Override
            protected StringBuilder doInBackground(Void... params) {
                StringBuilder builder = new StringBuilder();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(location));
                    char[] buffer = new char[1024*10];
                    int count = 0;
                    while ((count = bufferedReader.read(buffer))!=-1){
                        builder.append(buffer, 0, count);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return builder;
            }

            @Override
            protected void onPostExecute(StringBuilder stringBuilder) {
                editText.setText(stringBuilder.toString());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
