package izik.manager.travles.com.filesmanager;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dell on 3/6/2015.
 */
public class ExplorerListAdapter extends ArrayAdapter<FileInfo> {

    private ArrayList<FileInfo> list;
    boolean selectedMode;
    public ExplorerListAdapter(Context context, ArrayList<FileInfo> infos) {
        super(context, R.layout.simple_row, R.id.row_tv);
        list = infos;
    }

    public void setList(ArrayList<FileInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FileInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View v;
       if(convertView == null) {
           v = LayoutInflater.from(getContext()).inflate(R.layout.simple_row, null);
           holder = new Holder();
           holder.imageView = (ImageView) v.findViewById(R.id.imageView);
           holder.checkBox = (CheckBox) v.findViewById(R.id.multipleCheck);
           holder.tv = (TextView) v.findViewById(R.id.row_tv);
           v.setTag(holder);
       }else{
           v = convertView;
           holder = (Holder) convertView.getTag();
       }
        holder.tv.setText(getItem(position).toString());
        holder.imageView.setImageBitmap(getItem(position).getIcon());

//        holder.imageView.setImageResource(R.drawable.ic_launcher);
        if(getItem(position).toString().endsWith(".png")||getItem(position).toString().endsWith(".jpg")){
            getItem(position).readInBg(this);
        }

        if(selectedMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(selectedItems.get(position));
        }
        else
            holder.checkBox.setVisibility(View.GONE);
        return v;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }

    class Holder{
        TextView tv;
        ImageView imageView;
        CheckBox checkBox;
    }

    public void setSelectableMode(ViewGroup parent){
        selectedMode = true;
        notifyDataSetChanged();
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            ((Holder)parent.getChildAt(i).getTag()).checkBox.setVisibility(View.VISIBLE);
//        }
    }
    public void setUnSelectableMode(ViewGroup parent){
        selectedMode = false;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    SparseBooleanArray selectedItems = new SparseBooleanArray();
    public void setSelectedItem(int position, boolean checked){
        if(checked)
            selectedItems.put(position, checked);
        else
            selectedItems.delete(position);
        notifyDataSetChanged();
    }

    public FileInfo[] getSelected(){
        int count = selectedItems.size();

        FileInfo[] sel = new FileInfo[count];
        for (int i = 0; i < count; i++) {
            sel[i] = getItem(selectedItems.keyAt(i));
        }
        return sel;
    }


}
