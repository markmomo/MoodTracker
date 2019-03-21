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
import static markmomo.com.moodtracker.models.UserMoods.MOODS;
import static markmomo.com.moodtracker.models.UserNotes.CURRENT_NOTES;
import static markmomo.com.moodtracker.models.UserNotes.NOTES;
import static markmomo.com.moodtracker.tools.AlarmReceiver.DAY_COUNTER;


public class MainActivity extends AppCompatActivity {

    SharedPreferences mPrefs;

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

        mPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        mPrefs.edit().putInt(DAY_COUNTER, 0).apply();

        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);
        mUserMoods = new UserMoods(this);
        mUserNotes = new UserNotes(this);
        mNoteBox = new EditText(this);

        configureViewPager();
        startAlarm ();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPrefs.edit().putString(CURRENT_MOOD,mPager.getCurrentItem()+"").apply();
        mPrefs.edit().putInt(DAY_COUNTER, 0).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.listeningViewPager();
        mPrefs.edit().putInt(DAY_COUNTER, 0).apply();
    }

    private void configureViewPager(){

        mPager = findViewById(R.id.act_main_view_pager);

        SmileysAdapter smileysAdapter;
        smileysAdapter = new SmileysAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        mPager.setAdapter(smileysAdapter);

        if (mPrefs.getString(CURRENT_MOOD,"no mood").equals("no mood")){
            mPager.setCurrentItem(3);
            mPrefs.edit().putString(CURRENT_MOOD,"3").apply();
        } else {
            mPager.setCurrentItem(Integer.parseInt(mPrefs.getString(CURRENT_MOOD,"3")));
        }

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

                mPrefs.edit().putString(CURRENT_MOOD, mPager.getCurrentItem()+"").apply();
                mPrefs.edit().putInt(DAY_COUNTER, 0).apply();

                System.out.println(mPrefs.getString(MOODS,"no mood"));
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
                System.out.println(mPrefs.getString(NOTES,"no note"));
                System.out.println(mUserNotes.getNotes());
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                mPrefs.edit().putString(CURRENT_NOTES, mNoteBox.getText().toString()).apply();
                mPrefs.edit().putInt(DAY_COUNTER, 0).apply();

                mNoteBox.setText(null);
                System.out.println(mPrefs.getString(NOTES,"no note"));
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