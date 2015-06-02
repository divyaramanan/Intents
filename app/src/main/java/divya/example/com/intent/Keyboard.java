package divya.example.com.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class Keyboard extends ActionBarActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.home);
        actionBar.setIcon(R.drawable.home);
        actionBar.setTitle("KeyBoard");

        Bundle textData = getIntent().getExtras();
        String texty = textData.getString("text");
        EditText et = (EditText)findViewById(R.id.editText2);
        et.setText(texty);

        if(et.getText().toString().length()!=0)
            et.requestFocus(View.FOCUS_FORWARD);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keyboard, menu);
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
                Intent goHome = new Intent(this,MainActivity.class);
                boolean foundHome = navigateUpTo(goHome);


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

    public void hide(View button2) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
       View view = this.getCurrentFocus();
        if(view!=null)
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    public void back(View button3) {
        Intent goHome = new Intent(this,MainActivity.class);
        boolean foundHome = navigateUpTo(goHome);
    }

}
