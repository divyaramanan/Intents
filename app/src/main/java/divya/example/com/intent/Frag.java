package divya.example.com.intent;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divya on 2/13/2015.
 */
public class Frag extends ListFragment {
    int prePos=-1;
    ListView listView;
    List<String> list=new ArrayList<String>();
    ArrayAdapter adapter;
    AdapterView.AdapterContextMenuInfo info;
    int selectedchoice=-1;
    int[] flag=new int[5];
    Communicator comm;






    @Override
    public View onCreateView(LayoutInflater li,ViewGroup container,Bundle saveInstance)
    {
        View v= li.inflate(R.layout.frag_layout,container,false);
       // listView = (ListView)getListView().findViewById(R.id.list);
        return v;
    }

    public void collectchoice(int choice)
    {
        selectedchoice=choice;



    }


    @Override
    public void onActivityCreated(Bundle saveInstance) {
        super.onActivityCreated(saveInstance);
        //getActivity().findViewById(R.id.list).setBackgroundColor(Color.BLUE);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.desert, android.R.layout.simple_list_item_checked);
        setListAdapter(adapter);
        registerForContextMenu(getListView());
       getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       getListView().setItemChecked(selectedchoice,true);
       getListView().setSelection(selectedchoice);
        //getListView().setSelector(android.R.color.holo_blue_bright);
       getListView().performItemClick(null,selectedchoice,getListView().getItemIdAtPosition(selectedchoice));

        //getView(selectedchoice,null,getListView()).setBackgroundColor(Color.BLUE);







        comm = (Communicator)getActivity();
       storechoice();


        // v.setBackgroundColor(Color.BLUE);


    }


    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = getActivity().getLayoutInflater().inflate(R.layout.frag_layout, parent, false);

        }

        return convertView;
    }



    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }




    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();


        switch(id){
            case R.id.Select:

                getListView().setSelection(info.position);
                getListView().setItemChecked(info.position, true);
                flag[info.position]=1;
                selectedchoice=info.position;
                info.targetView.setBackgroundColor(Color.CYAN);
                getListView().performItemClick(info.targetView,info.position,R.id.list);
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                       if(flag[position]==1)
                       {
                           selectedchoice=position;

                        for (int i = 0; i < adapter.getChildCount(); i++) {
                            if (i == position) {

                                if(position!=prePos){
                                    adapter.getChildAt(i).setBackgroundColor(Color.CYAN);

                                    prePos=position;

                                }else{
                                    adapter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                    prePos=-1;
                                }

                            }else{

                                adapter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);

                            }
                        }




                       }}

                });


                storechoice();
                 flag[info.position]=0;
                return true;

            default:
                return super.onContextItemSelected(menuItem);



    }}



    private void storechoice() {

       comm.storedata(selectedchoice);
    }



    @Override
    public void onCreateContextMenu(ContextMenu contextMenu,View v,ContextMenu.ContextMenuInfo info)
    {
        super.onCreateContextMenu(contextMenu, v, info);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.context,contextMenu);
    }


}

