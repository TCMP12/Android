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

        View row;


        if(convertView == null){//create new row view
            row = LayoutInflater.from(context).inflate(R.layout.row, null);
            TextView positionView = (TextView) row.findViewById(R.id.posCreate);
            positionView.setText("pos: "+posCreate++);
            CheckBox cb = (CheckBox) row.findViewById(R.id.cb);
            cb.setOnCheckedChangeListener(this);
        }else{//recycle row view
            row = convertView;
        }

        TextView name = (TextView) row.findViewById(R.id.nameText);
        TextView phone = (TextView) row.findViewById(R.id.phoneText);

        CheckBox cb = (CheckBox) row.findViewById(R.id.cb);

        final Person current = getItem(position);

        name.setText(current.name);
        phone.setText(current.phone);

        cb.setTag(current);
        cb.setChecked(current.checked);

        return row;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Person current = (Person) buttonView.getTag();
        current.checked = isChecked;
    }
}
