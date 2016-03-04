package izik.manager.travles.com.filesmanager;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 3/5/2015.
 */
public class FileManager {

    /**
     * callback to notify view controller
     * about current directory changed to update UI.
     */
    public interface OnUpdateViewCurrentFile{
        void onChangeCurrentFile(DirectoryInfo current, ArrayList<FileInfo> innerFiles);
        void onUpdate();
        void onSaveDirectoryState(DirectoryInfo info);
    }



    //static variable shared of all FileManager instance
    //to update instance if changed something at current directory used
    private static Map<FileManager, String> currentDirs = new HashMap<>();

    //Current directory
    //Directory, path, and inner directory files
    private DirectoryInfo currentDirectory;
    private  String path = "";
    private final ArrayList<FileInfo> innerFiles = new ArrayList<>();


    private ArrayList<FileInfo> copied = new ArrayList<>();
    private ArrayList<DirectoryInfo> transaction = new ArrayList<>();
    //private int currentPosition;
    private boolean updateList;
    //listener on current directory changed. use work if only setOnUpdateViewCurrentFile called
    private OnUpdateViewCurrentFile onUpdateViewCurrentFile;
    //save frames user visit.


    /**
     * Create new instance.
     * start with Root External directory
     */
    public FileManager() {
        currentDirectory = DirectoryInfo.getRootDirectory();
        currentDirs.put(this, path);
        innerFiles.addAll(currentDirectory.getFilesInfo());
    }


    //add, delete , change file.

    //change current directory
    //back (transaction), set(insert to inner directory), parent (replace with parent current directory)
//    public void setCurrentPosition(int currentPosition) {
//        this.currentPosition = currentPosition;
//    }
//
//    public int getCurrentPosition() {
//        return currentPosition;
//    }




    public  ArrayList<FileInfo> moveToParent(){
        if(! path.equals(DirectoryInfo.root)){
            setCurrentFiles(currentDirectory.parent, false, false);
            return innerFiles;
        }
        return null;
    }

    public boolean goBack(){
        if(transaction.size()>0){
            setCurrentFiles(transaction.remove(transaction.size() - 1), false, false);
            if(onUpdateViewCurrentFile!= null)
                onUpdateViewCurrentFile.onChangeCurrentFile(currentDirectory, innerFiles);
            return true;
        }

        return false;
    }

    private final static int ADD_FILE=0,ADD_FOLDER=1;
    private void onChangeCurrentFilesData(int type){
        if(type == ADD_FILE || type == ADD_FOLDER)
            refreshData();
    }

    public DirectoryInfo getCurrentDirectory() {
        return currentDirectory;
    }

    private void refreshData() {

    }

    private void setCurrentFiles(DirectoryInfo currentDirectory, boolean notify, boolean trans){
        if(trans){
            transaction.add(this.currentDirectory);
        }
        if(onUpdateViewCurrentFile!=null)
            onUpdateViewCurrentFile.onSaveDirectoryState(this.currentDirectory);
        if(currentDirs.keySet().contains(this))
            currentDirs.remove(this);
        this.currentDirectory = currentDirectory;
        path = currentDirectory.getFile().getPath();
        currentDirs.put(this, path);
        innerFiles.clear();
        innerFiles.addAll(currentDirectory.getFilesInfo());
        if(notify && onUpdateViewCurrentFile!=null)
            onUpdateViewCurrentFile.onChangeCurrentFile(currentDirectory, innerFiles);

    }

    public void setCurrentFiles(DirectoryInfo currentDirectory, boolean notify){
        setCurrentFiles(currentDirectory, notify, true);
    }

    public ArrayList<FileInfo> getInnerFiles() {
        return innerFiles;
    }

    public void setOnUpdateViewCurrentFile(OnUpdateViewCurrentFile onUpdateViewCurrentFile) {
        this.onUpdateViewCurrentFile = onUpdateViewCurrentFile;
    }

    public String getPath() {
        return path;
    }



    public void addNewFile(String name){
        File file = new File(currentDirectory.getFile(), name);
        if(file.exists()) {
            //...
        }else{
            try {
                if(file.createNewFile()){
                    FileInfo added = new FileInfo();
                    innerFiles.add(added);
                    if(currentDirs.values().contains(path)){
                        for (Map.Entry<FileManager, String> entry : currentDirs.entrySet()) {
                            if(entry.getValue().equals(path))
                                entry.getKey().onChangeCurrentFilesData(ADD_FILE);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static final int SORT_AB=0, SORT_SIZE = 1 , SORT_DATE = 2;

    public void sortList(int sort){
        if(currentDirectory.sort(sort)){
           innerFiles.clear();
            innerFiles.addAll(currentDirectory.getFilesInfo());
            if(onUpdateViewCurrentFile != null)
                onUpdateViewCurrentFile.onUpdate();
        }
    }

    public void addNewFolder(String name){
        File file = new File(currentDirectory.getFile(), name);
        if(file.exists()) {
            //...
        }else{
                if(file.mkdir()){
                    DirectoryInfo added = new DirectoryInfo(file);
                    innerFiles.add(added);
                    if(currentDirs.values().contains(path)){
                        for (Map.Entry<FileManager, String> entry : currentDirs.entrySet()) {
                            if(entry.getValue().equals(path))
                                entry.getKey().onChangeCurrentFilesData(ADD_FOLDER);
                        }
                    }
                }
        }
    }

    //cut, copy and paste
    public void setCopied(FileInfo... copies) {
        this.copied.clear();
        this.copied.addAll(Arrays.asList(copies));
    }
    public int getCopyCount(){
        return this.copied.size();
    }
    public void paste(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                for (FileInfo info : copied) {
                    try {
                        copy(currentDirectory, info);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
            private void copy(DirectoryInfo parent, FileInfo fileInfo) throws IOException {
                if(! (fileInfo instanceof DirectoryInfo)){
                   File file = new File(parent.getFile(), fileInfo.toString());
                    file.createNewFile();
                    copyData(fileInfo.getFile(), file);
                }else{
                    File dir = new File(parent.getFile(), fileInfo.toString());
                    dir.mkdir();
                    DirectoryInfo dirInfo = new DirectoryInfo(dir);
                    for (FileInfo info : ((DirectoryInfo) fileInfo).getFilesInfo()) {
                        copy(dirInfo, info);
                    }
                }
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                currentDirectory.isLoadFiles = false;
                innerFiles.clear();
                innerFiles.addAll(currentDirectory.getFilesInfo());
                if(onUpdateViewCurrentFile != null)
                    onUpdateViewCurrentFile.onUpdate();
            }

            private void copyData(File src, File copy) {
                try {
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(copy));
                    int counter = 0;
                    byte[] buffer = new byte[2048];
                    while ((counter = in.read(buffer)) != -1){
                        out.write(buffer, 0 , counter);
                    }
                    in.close();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

}
