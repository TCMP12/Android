package izik.manager.travles.com.filesmanager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import manager.views.LocationView;

/**
 * Created by dell on 3/4/2015.
 */
public class ExplorerFragment extends Fragment
        implements LocationView.OnLocationChangeListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
        , FileManager.OnUpdateViewCurrentFile{

    private int selection;
    Runnable run = new Runnable() {

        @Override
        public void run() {
            listView.setSelection(selection);
            selection = 0;
        }
    };

    public static  final int LIST = 1, LARGE_ICON = 2, GRID = 3;
    private static final String ACTION_DEBUG = "action mode";
    private FileManager manager = new FileManager();

    private ListView listView;
    //DirectoryInfo parent = new DirectoryInfo(Environment.getExternalStorageDirectory());
    ArrayList<FileInfo> listFiles;// = new ArrayList<>();
    OnFileSelectedListener onFileSelectedListener;
    private boolean listSet;
    private ExplorerListAdapter adapter;
    ActionMode actionMode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setNewLocation(DirectoryInfo parent) {
        adapter.setList(listFiles = manager.getInnerFiles());
        getListAdapter().notifyDataSetInvalidated();
        locationView.setLocations(parent.getDirectories());
    }

    private ArrayAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public void onLocationChange(Object selected) {
        manager.setCurrentFiles((DirectoryInfo) selected, false);
        setNewLocation((DirectoryInfo) selected);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileInfo selected = manager.getInnerFiles().get(position);
        if(selected instanceof  DirectoryInfo && ((DirectoryInfo) selected).getFile().canRead()){
            manager.setCurrentFiles((DirectoryInfo)selected, false);
            setNewLocation((DirectoryInfo) selected);
        }else{
            if(selected.toString().endsWith(".png")||selected.toString().endsWith(".jpg") ){
                PictureFragment pictureFragment = new PictureFragment();
                Bundle bundle = new Bundle();
                bundle.putString("path", selected.getFile().getPath());
                pictureFragment.setArguments(bundle);
                pictureFragment.show(getActivity().getSupportFragmentManager(),"");
            }else if(selected.toString().endsWith(".txt")){
                Intent intent = new Intent(getActivity(), NotepadActivity.class);
                intent.setData(Uri.fromFile(selected.getFile()));
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(intent);
            } else{
                openFile(selected.getFile());
            }
        }
    }

    private void openFile(File file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
        intent.setDataAndType(uri, type == null ? "*/*" : type);
//        startActivity((Intent.createChooser(intent,
//                getString(R.string.abc_action_bar_home_description))));
        startActivity(intent);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(manager.getInnerFiles().get(position) instanceof DirectoryInfo){
            DetailsFragment fragment = new DetailsFragment();
            fragment.setFileInfo(manager.getInnerFiles().get(position));
            fragment.show(getActivity().getSupportFragmentManager(),"a");
        }
        return true;
    }

    public boolean onBackPressed() {
        if(actionMode!= null) {
            actionMode.finish();
            return true;
        }
        return manager.goBack();
    }

    public AbsListView.MultiChoiceModeListener getListener() {
        return new AbsListView.MultiChoiceModeListener() {
            int count = 0;
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                adapter.setSelectedItem(position, checked);
                if(checked)
                    count++;
                else
                    count--;
                mode.setTitle(count+"selected");

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.context_menu,menu);

                actionMode = mode;
                adapter.setSelectableMode(listView);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if(item.getItemId() == R.id.m_copy) {
                    manager.setCopied(adapter.getSelected());
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.setUnSelectableMode(listView);
                actionMode = null;
            }
        };
    }

    @Override
    public void onChangeCurrentFile(DirectoryInfo current, ArrayList<FileInfo> innerFiles) {
        setNewLocation(current);
        if(current.isSave()){
            selection = current.getPositionList();
            listView.post(run);
        }

    }

    @Override
    public void onUpdate() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveDirectoryState(DirectoryInfo info) {
        info.setSave(true);
        info.setPositionList(listView.getFirstVisiblePosition());
    }


    public FileManager getManager() {
        return manager;
    }

    public void setViewAdapter(ExplorerListAdapter viewAdapter) {
        this.listView.setAdapter(adapter);
    }

    public interface OnFileSelectedListener{
        //void onFileSelect(FileInfo list, int position, boolean isLong);
        void onFileSelect(DirectoryInfo selected);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ExplorerListAdapter(getActivity(), manager.getInnerFiles()));
        manager.getInnerFiles();
        manager.setOnUpdateViewCurrentFile(this);
    }


    private void setListAdapter(ExplorerListAdapter fileArrayAdapter) {
        this.adapter = fileArrayAdapter;
    }



    LocationView locationView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationView = new LocationView(getActivity());
        listView = new ListView(getActivity());
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(getListener());
        listView.setId(android.R.id.list);
        listView.setOnItemClickListener(this);
        if (adapter != null)
            setViewAdapter(adapter);

        locationView.setContentView(listView);
        locationView.setLocations(manager.getCurrentDirectory().getDirectories());
        locationView.setOnLocationChangeListener(this);
        return locationView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.explorer_menu, menu);
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("path", manager.getPath());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.m_paste:
                manager.paste();
                break;
        }
        return true;
    }

}
