package tcmp.uniquename.com.mygrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by tcmp on 17/02/2016.
 */
public class MyAdapter extends BaseAdapter {
    private final Context context;
    ArrayList<Integer> dataSet = new ArrayList<>();
    int[] drawabals = {R.drawable.image_custom,
            R.drawable.image_customb,
            R.drawable.image_customc,
            android.R.drawable.btn_default};

    public MyAdapter(Context context) {
        this.context = context;

        for (int i = 0; i < 50 ; i++) {
            dataSet.add(drawabals[i % 4]);
        }
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Integer getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.cel, null);
        ImageView imageView = (ImageView) convertView;
        imageView.setImageResource(getItem(position));

        return imageView;
    }
}
