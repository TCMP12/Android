package manager.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import izik.manager.travles.com.filesmanager.DirectoryInfo;
import izik.manager.travles.com.filesmanager.R;

/**
 * Created by dell on 3/4/2015.
 */
public class LocationView extends LinearLayout implements View.OnClickListener {



    private ArrayList<Item> list = new ArrayList<>();
    OnUpdateItemListener onUpdateItemListener;

    public void setOnUpdateItemListener(OnUpdateItemListener onUpdateItemListener) {
        this.onUpdateItemListener = onUpdateItemListener;
    }

    @Override
    public void onClick(View v) {
        if(onLocationChangeListener != null){
            onLocationChangeListener.onLocationChange(v.getTag());
        }
    }

    public interface OnUpdateItemListener{
        void onUpdateItem(Item item);
    }
    public interface OnLocationChangeListener{
        void onLocationChange(Object selected);
    }

    OnLocationChangeListener onLocationChangeListener;

    public void setOnLocationChangeListener(OnLocationChangeListener onLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener;
    }

    boolean removeOnLowSelected = true;
    int maxLocations = 100;
    LinearLayout bar;
    FrameLayout content;
    HorizontalScrollView horizontalScrollView;

    int bg_res = -1;
    Bitmap bg_bitmap = null;

    public LocationView(Context context) {
        super(context);
        init();

    }

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        horizontalScrollView = new HorizontalScrollView(getContext());
        bar = new LinearLayout(getContext());
        bar.setOrientation(HORIZONTAL);
        horizontalScrollView.addView(bar);
        horizontalScrollView.setVerticalScrollBarEnabled(false);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.setBackgroundResource(R.drawable.bg_border);
        horizontalScrollView.setPadding(4,4,4,4);
        addView(horizontalScrollView);

        content = new FrameLayout(getContext());
        content.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(content);
    }

    public void setItemBackgroundBitmap(Bitmap b){
//        Drawable s = null;
//        Drawable.
        this.bg_bitmap = b;
    }
    public void setItemBackgroundResource(int resource){
        this.bg_res = resource;
        setItemBackgroundBitmap(BitmapFactory.decodeResource(getResources(), resource));
    }

    public void setLocations(ArrayList<DirectoryInfo> labels){

        int count = bar.getChildCount();
        int cursor = 0;
        for (int i = 0; i < Math.min(labels.size(), count); i++, cursor++) {
            TextView tv = (TextView) bar.getChildAt(i);
            tv.setText(labels.get(i).toString());
            tv.setTag(labels.get(i));
        }
        if(cursor <= count){
            for (int i = cursor; i < labels.size(); i++) {
                addLocation(labels.get(i));
            }
        }

        if(count> cursor){
            bar.removeViews(count - (count - cursor), (count - cursor));
        }
    }

    public void addLocation(){
        addLocation(null);
    }
    public void addLocation(Object label){
        addLocation(label, null);
    }
    public void addLocation(Object label, View view){
        if(view == null){
            TextView defView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.simple_location_item_1,null);
            if(label != null)
                defView.setText(label.toString());
            defView.setTag(label);
            defView.setOnClickListener(this);
            bar.addView(defView);
            if(bar.getChildCount()>2) {
                View v = bar.getChildAt(bar.getChildCount() - 2);
                horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        horizontalScrollView.fullScroll(FOCUS_RIGHT);
                    }
                });


            }
        }
    }


    public void setContentView(View view){
        this.content.addView(view);
    }

    public static class Item{
        private Object tag;
        private String label;
        private int id;
        private int recId;
        private View view;
        protected TextView textView;
        private boolean userViewCompleted;


        protected boolean isUserViewCompleted() {
            return userViewCompleted;
        }

        /**
         *
         * @param view optional,
         *             default is TextView, anyway you can set background view by LocationView methods
         * @param resId id for textView in the view, if you want set text on the specify textView (if -1 not find textView)
         * @param tag optional
         * @param label String, that set in the default or another textView
         */
        public Item(View view,int resId , Object tag, String label) {
            if(view != null) {
                this.view = view;
                if(resId != -1){
                    this.textView = (TextView) view.findViewById(resId);
                    this.recId = resId;
                    textView.setText(label);
                }
                userViewCompleted = true;
            }
            this.tag = tag;
            this.label = label;
        }
    }
}
