package izik.manager.travles.com.filesmanager;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by dell on 3/5/2015.
 */
public class DirectoryInfo extends  FileInfo{

    private File myDir;
    ArrayList<FileInfo> files = new ArrayList<>();
    ArrayList<DirectoryInfo> directoryInfo = new ArrayList<>();
    ArrayList<FileInfo> info = new ArrayList<>();
    boolean isLoadFiles = false;
    int sortType = SORT_DATE;

    private static DirectoryInfo rootDirectory = new DirectoryInfo();
    DirectoryInfo parent;
    private int positionList;
    private boolean save;
    public boolean updateInner;

    public boolean isRoot() {
        return myDir.getPath().equals(root);
    }

    public File getFile(){
        return myDir;
    }
    public static DirectoryInfo getRootDirectory(){
        return rootDirectory;
    }
    static  String root = Environment.getExternalStorageDirectory().getPath();
    private boolean rootFile;

    private DirectoryInfo (){
        myDir = Environment.getExternalStorageDirectory();
        rootFile = true;

        if(myDir.list()!= null && myDir.list().length>0)
            icon = fullFolderIcon;
        else
            icon = folderIcon;
    }

    private void loadFiles() {
        if(isLoadFiles)
            return;
        info.clear();
        myDir = new File(myDir.getPath());
        for (File file : myDir.listFiles()) {
            if(file.isDirectory()){
                DirectoryInfo directoryInfo = new DirectoryInfo(file, this);
                this.directoryInfo.add(directoryInfo);
                info.add(directoryInfo);
            }else{
                FileInfo fileInfo = new FileInfo(file,this);
                files.add(fileInfo);
                info.add(fileInfo);
            }
        }
        isLoadFiles = true;

    }

    public DirectoryInfo(File myDir) {
        this.myDir = myDir;
        if(myDir.list()!= null && myDir.list().length>0)
            icon = fullFolderIcon;
        else
            icon = folderIcon;

        if(myDir.getParentFile() == null && myDir.getPath().equals(root)){
            rootFile = true;
            return;
        }else if(myDir.getParentFile() == null){
            parent = rootDirectory;
            return;
        }
            parent = new DirectoryInfo(myDir.getParentFile());
    }
    public DirectoryInfo(File myDir, DirectoryInfo parent) {
        this.myDir = myDir;
        this.parent = parent;

        if(myDir.list()!= null && myDir.list().length>0)
            icon = fullFolderIcon;
        else
            icon = folderIcon;

    }


    public ArrayList<DirectoryInfo> getDirectories(){
        ArrayList<DirectoryInfo> list = new ArrayList<>();
        DirectoryInfo info = this;
        list.add(this);
        while (! info.rootFile){
            info = info.parent;
            list.add(0, info);
        }
        return  list;
    }

    public ArrayList<FileInfo> getFilesInfo(){
        loadFiles();
        return  info;
    }

    @Override
    public String toString() {
        return myDir.getName();
    }

    public int getDirCount() {
        loadFiles();
        return directoryInfo.size();
    }

    public int getFilesCount() {
        loadFiles();
        return files.size();
    }

    public boolean sort(final int sortType){
        if(sortType != this.sortType) {
            Collections.sort(info, new Comparator<FileInfo>() {
                @Override
                public int compare(FileInfo lhs, FileInfo rhs) {
                    switch (sortType){
                        case SORT_AB:
                            return lhs.toString().compareTo(rhs.toString());
                        case SORT_DATE:
                            return (int)(lhs.getFile().lastModified() - rhs.getFile().lastModified());
                        case SORT_SIZE:
                            return (int)(lhs.getFile().length() - rhs.getFile().length());
                    }
                    return 0;
                }
            });
            this.sortType = sortType;
            return true;
        }
        return false;
    }

    public void setPositionList(int positionList) {
        this.positionList = positionList;
    }

    public int getPositionList() {
        return positionList;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isSave() {
        return save;
    }
}
