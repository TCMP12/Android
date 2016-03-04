package izik.manager.travles.com.filesmanager;

import android.app.Application;

/**
 * Created by dell on 3/6/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileInfo.init(this);
    }
}
