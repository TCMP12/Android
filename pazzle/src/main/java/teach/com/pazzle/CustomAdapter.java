package teach.com.pazzle;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by dell on 7/4/2015.
 */
public class CustomAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    MainActivity activity;
    int[] imges = {R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f,
            R.drawable.g, R.drawable.h, R.drawable.i};
    Item[] items = new Item[imges.length];

    public CustomAdapter(MainActivity mainActivity) {
        this.activity = mainActivity;

        initRandomItems();
    }

    private void initRandomItems() {
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            list.add(i);
        }

        for (int i = 0; i < items.length; i++) {
            int index = random.nextInt(list.size());
            int position = list.get(index);
            Item item = new Item();
            item.currentPosition = i;
            item.truePosition = position;
            item.drawableRes = imges[position];
            items[i] = item;
            list.remove(index);
        }

    }


    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Item getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(activity);
            imageView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else
            imageView = (ImageView) convertView;
        imageView.setImageResource(getItem(position).drawableRes);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAlpha(getItem(position).isSelect ? 0.5f : 1.0f);

        return imageView;
    }


    int selected = -1;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(selected != -1){
            Item temp = items[selected];
            items[selected] = items[position];
            items[position] = temp;
            selected = -1;
            temp.isSelect = false;
        }else {
            selected = position;
            items[position].isSelect = true;
        }

        notifyDataSetChanged();
    }


}
