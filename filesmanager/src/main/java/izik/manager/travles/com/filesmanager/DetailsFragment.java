package izik.manager.travles.com.filesmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dell on 3/6/2015.
 */
public class DetailsFragment extends DialogFragment {


    DirectoryInfo fileInfo;

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = (DirectoryInfo)fileInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_layout,null);
        TextView numDir = (TextView) v.findViewById(R.id.numberDir);
        TextView numFile = (TextView) v.findViewById(R.id.numberFile);
        numDir.setText(""+fileInfo.getDirCount());
        numFile.setText(""+fileInfo.getFilesCount());
        return v;
    }


}
