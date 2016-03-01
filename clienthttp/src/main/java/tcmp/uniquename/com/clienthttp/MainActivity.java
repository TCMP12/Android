package tcmp.uniquename.com.clienthttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postToComputer();
    }

    private void postToComputer() {
        new Thread(){
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL("http://10.0.0.105:8080").openConnection();
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write("123=221&name=izik\r\n\r\n".getBytes());
                    connection.connect();

                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    connection.disconnect();
                }
            }
        }.start();
    }

    public  void test(){

    }
}
