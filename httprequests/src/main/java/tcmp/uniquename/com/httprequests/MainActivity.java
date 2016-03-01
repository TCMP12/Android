package tcmp.uniquename.com.httprequests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDownloadImage();
    }

    private void startDownloadImage() {
        new AsyncTask<Void, Void, Bitmap>(){

            @Override
            protected Bitmap doInBackground(Void... params) {
                HttpURLConnection connection =null;
                try {
                    URL mUrl = new URL("http://www.israelhayom.co.il/sites/all/themes/ihtheme/logo.png");
                    connection = (HttpURLConnection) mUrl.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    connection.disconnect();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if(bitmap != null)
                    ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
            }
        }.execute();
    }

    private void startDownload() {
        if(checkConnection()){
            new AsyncTask<Void, Void , String>(){

                @Override
                protected String doInBackground(Void... params) {

                    HttpURLConnection connection = null;
                    try {
                        URL mUrl = new URL("http://israelhayom.co.il");
                        connection = (HttpURLConnection) mUrl.openConnection();
                        InputStream inputStream = connection.getInputStream();
                        Scanner scanner = new Scanner(inputStream);
                        StringBuilder builder = new StringBuilder();

                        while (scanner.hasNextLine()){
                            builder.append(scanner.nextLine()+"\n");
                        }
                        return builder.toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(connection != null)
                            connection.disconnect();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    if(s!= null)
                        ((TextView)findViewById(R.id.textHtml)).setText(s);
                }
            }.execute();
        }
    }

    private boolean checkConnection() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info.getType() == ConnectivityManager.TYPE_WIFI){

        }

        return info == null ? false :  info.isConnected();
    }
}
