package markmomo.com.moodtracker.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import markmomo.com.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    public void button7IsClicked(View view){
        Log.e("HistoryActivity Button", "button7IsClicked: ");
    }
    public void button6IsClicked(View view){
        Log.e("HistoryActivity Button", "button6IsClicked: ");
    }
    public void button5IsClicked(View view){
        Log.e("HistoryActivity Button", "button5IsClicked: ");
    }
    public void button4IsClicked(View view){
        Log.e("HistoryActivity Button", "button4IsClicked: ");
    }
    public void button3IsClicked(View view){
        Log.e("HistoryActivity Button", "button3IsClicked: ");
    }
    public void button2IsClicked(View view){
        Log.e("HistoryActivity Button", "button2IsClicked: ");
    }
    public void button1IsClicked(View view){
        Log.e("HistoryActivity Button", "button1IsClicked: ");
    }
}
