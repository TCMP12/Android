package manager.views;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import izik.manager.travles.com.filesmanager.R;

/**
 * Created by dell on 3/17/2015.
 */
public class ExplorerBar implements View.OnClickListener {

    LinearLayout linearLayout;
    Context context;
    final ImageView add;

    public ExplorerBar(Context context) {
        this.context = context;
        linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bar_layout, null);
        add = (ImageView) linearLayout.findViewById(R.id.addFolder);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addFolder:
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.inflate(R.menu.explorer_menu);
                popupMenu.show();
                break;
        }
    }

    public View getView() {
        return linearLayout;
    }
}
