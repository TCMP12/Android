package tcmp.uniquename.com.app_21_02_16;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by tcmp on 21/02/2016.
 */
public class MyAdapter extends ArrayAdapter<String> {

    static ArrayList<String> dataSet = new ArrayList<>();

    public MyAdapter(Context context, int resource) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public String getItem(int position) {
        return dataSet.get(position);
    }


    int countRequest;

    @Override
    public Filter getFilter() {
        return new Filter() {

            //Background Thread
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                countRequest++;
                FilterResults results = new FilterResults();
                results.count= countRequest;
                //results.values
//                MyAdapter.this.notifyDataSetChanged(); Error call from ui thread
                return null;
            }

            //UI THREAD (main thread)
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(countRequest != results.count)
                    return;
                dataSet.add("");
                MyAdapter.this.notifyDataSetChanged();

            }
        };
    }
}
