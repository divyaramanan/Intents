package divya.example.com.intent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class date extends ActionBarActivity {

    DatePicker dp;
    String datestr="";
    String dateinit="";
    boolean ifdatechange=false;
    int storedate,storemonth,storeyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.home);
        actionBar.setIcon(R.drawable.home);
        actionBar.setTitle("Date");

     dp=(DatePicker)findViewById(R.id.datePicker);

        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);

       SharedPreferences sp = getSharedPreferences("dates",Context.MODE_PRIVATE);
       cyear= sp.getInt("year",cyear);
       cmonth = sp.getInt("month",cmonth);
       cday = sp.getInt("day",cday);


       dateinit=cmonth+1+"-"+cday+"-"+cyear;
       dp.init(cyear, cmonth, cday, dateSetListener);




    }

    DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
            ifdatechange=true;
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            storedate = dayOfMonth;
            storemonth = monthOfYear;
            storeyear = year;
            datestr=monthOfYear+1+"-"+dayOfMonth+"-"+year;


        }
    };


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
                confirmdate();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            confirmdate();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void confirmdate(){
        AlertDialog.Builder cdialog = new AlertDialog.Builder(this);
        cdialog.setMessage("Do you want to save this date?");
        cdialog.setCancelable(false);

        //If the new date has to be sent to main Activity.java
        cdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getSharedPreferences("dates", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("day",storedate);
                editor.putInt("month",storemonth);
                editor.putInt("year",storeyear);
                editor.commit();
                Intent toPassBack = getIntent();
                toPassBack.putExtra("datestring", datestr);
                setResult(RESULT_OK, toPassBack);
                finish();
            }
        });

        //If no date has to be sent to mainActivity.java
        cdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent toPassBack = getIntent();
                toPassBack.putExtra("datestring","");
                setResult(RESULT_OK, toPassBack);
                finish();
            }
        });

        //If there is a change in date,confirm dialog will appear. Else the no date is sent to main Activity
        if(ifdatechange==true)
        cdialog.create().show();
        else
        {
            Intent toPassBack = getIntent();
            toPassBack.putExtra("datestring", "");
            setResult(RESULT_OK, toPassBack);
            finish();
        }

    }
}
