package markmomo.com.moodtracker.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.SystemClock;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.Calendar;

import markmomo.com.moodtracker.R;
import markmomo.com.moodtracker.models.UserMoods;
import markmomo.com.moodtracker.models.UserNotes;
import markmomo.com.moodtracker.tools.AlarmReceiver;
import markmomo.com.moodtracker.tools.SmileysAdapter;

import static markmomo.com.moodtracker.models.UserMoods.CURRENT_MOOD;
import static markmomo.com.moodtracker.models.UserNotes.CURRENT_NOTES;


public class MainActivity extends AppCompatActivity {

    UserMoods mUserMoods;
    UserNotes mUserNotes;
    ImageButton mNoteIcon,mHistoryIcon;
    ViewPager mPager;
    EditText mNoteBox;


    public void historyIconIsClicked (View view){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    public void noteIconIsClicked (View view) {
        displayNoteBox();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);
        mUserMoods = new UserMoods(this);
        mUserNotes = new UserNotes(this);
        mNoteBox = new EditText(this);

        configureViewPager();
        startAlarm ();
        listeningViewPager();
        mUserMoods.trackMoodsData();
        mUserNotes.trackNotesData();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mUserMoods.trackMoodsData();
        mUserNotes.trackNotesData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUserMoods.trackMoodsData();
        mUserNotes.trackNotesData();
    }

    private void configureViewPager(){

        mPager = findViewById(R.id.act_main_view_pager);

        SmileysAdapter smileysAdapter;
        smileysAdapter = new SmileysAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        mPager.setAdapter(smileysAdapter);

        mNoteIcon.setBackgroundColor(smileysAdapter.mainActivityIconsColors);
        mHistoryIcon.setBackgroundColor(smileysAdapter.mainActivityIconsColors);
    }

    private void startAlarm (){

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 30);

//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
//                1000 * 60 * 20, alarmIntent);

        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES/15,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES/15, alarmIntent);
    }

    private void listeningViewPager(){

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {
                SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                prefs.edit().putInt(CURRENT_MOOD, mPager.getCurrentItem()).apply();

                mUserMoods.trackMoodsData();
                mUserNotes.trackNotesData();
                System.out.println(mUserMoods.getMoods());

            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void displayNoteBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Cancel to keep last note\nOk to delete last note");
        alert.setTitle("Note");
        alert.setView(mNoteBox);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mNoteBox.setText(null);
                System.out.println(mUserNotes.getNotes());
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                prefs.edit().putString(CURRENT_NOTES, mNoteBox.getText().toString()).apply();

                mUserMoods.trackMoodsData();
                mUserNotes.trackNotesData();
                mNoteBox.setText(null);
                System.out.println(mUserNotes.getNotes());
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mNoteBox.getParent() != null) {
            ((ViewGroup)mNoteBox.getParent()).removeView(mNoteBox); // <- fix
        }
        alert.show();
    }
}