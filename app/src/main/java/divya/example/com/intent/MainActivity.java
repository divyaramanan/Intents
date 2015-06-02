package divya.example.com.intent;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements  Communicator{
String selected="";
 int selectedchoice = -1;
private static final int INTENT_EXAMPLE_REQUEST = 123;
private static final int INTENT_LIST_REQUEST=124;
    private int prePos=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.home);
        actionBar.setIcon(R.drawable.home);
        actionBar.setTitle("Home");
        SharedPreferences sp = getSharedPreferences("lists",Context.MODE_PRIVATE);
        selectedchoice= sp.getInt("choice",selectedchoice);

        android.app.FragmentManager fm = getFragmentManager();
        Frag f1=(Frag)fm.findFragmentById(R.id.fragment);
        f1.collectchoice(selectedchoice);





               handlespinner();
               //handleList();
    }

    private void handlespinner() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.actarray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
            {
                selected = parent.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                selected="nothing";

            }
        });

    }

   public void appmove(View button){

        if(selected.equals("Date")) {
            getdatemenu();
        }
       if(selected.equals("Keyboard")) {
          getkeymenu();
       }
       if(selected.equals("List")){
           getlistmenu();
       }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
               Intent goHome = new Intent(this,MainActivity.class);
               boolean foundHome = navigateUpTo(goHome);

       }



        return true;
    }



    private void getdatemenu()
    {
        Intent dtintent = new Intent(this,date.class);
        startActivityForResult(dtintent, INTENT_EXAMPLE_REQUEST);
    }
    private void getkeymenu()
    {
        EditText et = (EditText)findViewById(R.id.editText);
        Intent go = new Intent(this,Keyboard.class);
        go.putExtra("text",et.getText().toString());
        startActivity(go);
    }
    private void getlistmenu()
    {
        Intent go = new Intent(this,list.class);
        go.putExtra("choice",selectedchoice);
        startActivityForResult(go,INTENT_LIST_REQUEST);



    }

    @Override
    public void onDestroy(){
        super.onDestroy();


        SharedPreferences sp = getSharedPreferences("lists", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("choice",-1);
        editor.commit();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_EXAMPLE_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    String datestr = data.getStringExtra("datestring");
                    EditText dt = (EditText)findViewById(R.id.editText);
                    dt.setText(datestr);
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }

        if (requestCode == INTENT_LIST_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    selectedchoice = data.getIntExtra("choice",-1);


                    break;
                case RESULT_CANCELED:
                    break;
            }
        }

    }


    @Override
    public void storedata(int choice) {
        selectedchoice = choice;



    }
}
