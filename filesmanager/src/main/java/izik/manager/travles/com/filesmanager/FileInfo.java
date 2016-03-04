package izik.manager.travles.com.filesmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dell on 3/5/2015.
 */
public class FileInfo implements Comparable<FileInfo> {

    public static final int SORT_AB=0, SORT_SIZE = 1 , SORT_DATE = 2;
    public int sortType;

    static Context context;
    private File myFile;
    private DirectoryInfo parent;
    protected Bitmap icon;
    public boolean iconOnShow;

    static Bitmap folderIcon;
    static Bitmap fullFolderIcon;
    static Bitmap fileIcon;


    protected FileInfo(){
    }
    public FileInfo(File myFile, DirectoryInfo parent) {
        if (myFile.isDirectory())
            throw new IllegalArgumentException("file can't be directory");
        this.myFile = myFile;
        if (parent != null)
            this.parent = parent;
        else
            this.parent = new DirectoryInfo(myFile.getParentFile());


        if (myFile.getName().endsWith(".apk")) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(myFile.getAbsolutePath(), 0);
            if(pi != null) {
                ApplicationInfo appInfo = pi.applicationInfo;
                if (Build.VERSION.SDK_INT >= 8) {
                    appInfo.sourceDir = myFile.getPath();
                    appInfo.publicSourceDir = myFile.getPath();
                }
                BitmapDrawable drawable = (BitmapDrawable) appInfo.loadIcon(pm);
                icon = drawable.getBitmap();
            }
        } else if (!myFile.getName().endsWith(".png") && !myFile.getName().endsWith(".jpg")){
            icon = fileIcon;
        }
    }

    public File getFile() {
        return myFile;
    }

    boolean iconRead = false;
    public void readInBg(final ArrayAdapter notify) {
        if(iconRead)
            return;
        iconRead = true;
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 35;
                Bitmap b = BitmapFactory.decodeFile(myFile.getPath(),options);
                if(b!= null)
                    icon = b;
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(icon != null)
                    notify.notifyDataSetChanged();
            }
        }.execute();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }
    public FileInfo(File myFile) {
        this(myFile, null);
    }


    public Bitmap getIcon() {

        return icon;
    }

    @Override
    public String toString() {

        return myFile.getName();
    }

    public static void init(Context application) {
         context = application.getApplicationContext();
         folderIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.folder);
         fullFolderIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.folder_full);
         fileIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.text);

    }


    @Override
    public int compareTo(FileInfo another) {
        switch (sortType){
            case SORT_AB:
                return getFile().getName().compareTo(another.getFile().getName());
            case SORT_DATE:
                return (int) (getFile().lastModified() - another.getFile().lastModified());
            case SORT_SIZE:
                return (int) (getFile().length() - another.getFile().length());
        }
        return 0;
    }
}
