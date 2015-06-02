package divya.example.com.intent;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Divya on 2/13/2015.
 */
public class list extends ActionBarActivity implements Communicator {


    int selectedchoice=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.home);
        actionBar.setIcon(R.drawable.home);
        actionBar.setTitle("List");
        Bundle listdata = getIntent().getExtras();
        selectedchoice = listdata.getInt("choice");

        android.app.FragmentManager fm = getFragmentManager();
        Frag f1=(Frag)fm.findFragmentById(R.id.fragment2);
        f1.collectchoice(selectedchoice);


    }

    public int getchoice(){

        return selectedchoice;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.dates:
                getdatemenu();
                break;
            case R.id.keys:
                getkeymenu();
                break;
            case R.id.list:
                getlistmenu();
            case android.R.id.home:
                SharedPreferences sp = getSharedPreferences("lists", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("choice",selectedchoice);
                editor.commit();
                Intent go = new Intent(this,MainActivity.class);
                startActivity(go);

        }



        return true;
    }



    private void getdatemenu()
    {
        Intent dtintent = new Intent(this,date.class);
        startActivity(dtintent);
    }
    private void getkeymenu()
    {

        Intent go = new Intent(this,Keyboard.class);
        go.putExtra("text","");
        startActivity(go);
    }
    private void getlistmenu()
    {
        int tempselect=-1;
        Intent go = new Intent(this,list.class);
        go.putExtra("choice",tempselect);
        startActivity(go);



    }


    public void backclick(View button)
    {
        SharedPreferences sp = getSharedPreferences("lists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("choice",selectedchoice);
        editor.commit();

       Intent go = new Intent(this,MainActivity.class);
        startActivity(go);



    }




    @Override
    public void storedata(int choice) {
     selectedchoice=choice;

    }
}
