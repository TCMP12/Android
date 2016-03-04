package tcmp.uniquename.com.app_17_02_16;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tcmp on 17/02/2016.
 */
public class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    ArrayList<Person> dataSet;
    Context context;

    public MyAdapter(ArrayList<Person> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Person getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    int posCreate = 0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.nameText);
            holder.phone = (TextView) convertView.findViewById(R.id.phoneText);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);

            TextView posView = (TextView) convertView.findViewById(R.id.posCreate);
            posView.setText("pos: "+posCreate++);

            holder.cb.setOnCheckedChangeListener(this);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Person current = getItem(position);
        holder.name.setText(current.name);
        holder.phone.setText(current.phone);

        holder.cb.setTag(current);
        holder.cb.setChecked(current.checked);

        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Person current = (Person) buttonView.getTag();
        current.checked = isChecked;
    }

    static class ViewHolder{
        TextView name, phone;
        CheckBox cb;
    }
}
