package tcmp.uniquename.com.app_17_02_16;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<Person> list = new ArrayList<>();
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillList(50);
        adapter = new MyAdapter(list, this);

        ListView mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(this);
    }

    private void fillList(int max) {
        for (int i = 0; i < max; i++) {
            list.add(new Person("Person "+i, Math.random()*99999999+""));
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        list.add(0, new Person("Person last", "0554316782"));
        adapter.notifyDataSetChanged();

    }
}
