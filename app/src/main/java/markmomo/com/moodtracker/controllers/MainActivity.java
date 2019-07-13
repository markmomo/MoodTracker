package markmomo.com.moodtracker.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import markmomo.com.moodtracker.R;
import markmomo.com.moodtracker.tools.AlarmReceiver;
import markmomo.com.moodtracker.tools.MoodsAdapter;

import static markmomo.com.moodtracker.models.Preferences.getCurrentMoodFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.initializePrefs;
import static markmomo.com.moodtracker.models.Preferences.printPrefsData;
import static markmomo.com.moodtracker.models.Preferences.putAppStatusOnPrefs;
import static markmomo.com.moodtracker.models.Preferences.putCurrentMoodOnPrefs;
import static markmomo.com.moodtracker.models.Preferences.putCurrentNoteOnPrefs;
import static markmomo.com.moodtracker.models.Preferences.putDayCounterOnPrefs;
import static markmomo.com.moodtracker.models.Preferences.updateMoodsAndNotesPrefs;

public class MainActivity extends AppCompatActivity {

    private ImageButton mNoteIcon,mHistoryIcon;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);

        initializePrefs(this);
        this.configureViewPager();
        this.displayStartScreen();
        this.listeningViewPager();
        this.startAlarm();
        printPrefsData(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        putCurrentMoodOnPrefs(this,mViewPager.getCurrentItem()+"");
        updateMoodsAndNotesPrefs(this);
        printPrefsData(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        putAppStatusOnPrefs(this,"inactive");
        this.configureViewPager();
        this.displayStartScreen();
        this.listeningViewPager();
        updateMoodsAndNotesPrefs(this);
        printPrefsData(this);
    }

    public void historyIconIsClicked (View view){

        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
        printPrefsData(this);
    }

    public void noteIconIsClicked (View view) {
        displayNoteBox(this);
    }

    private void configureViewPager(){
        mViewPager = findViewById(R.id.act_main_view_pager);
        MoodsAdapter moodsAdapter = new MoodsAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));

        mViewPager.setAdapter(moodsAdapter);

        mNoteIcon.setBackgroundColor(moodsAdapter.mainActivityIconsColors);
        mHistoryIcon.setBackgroundColor(moodsAdapter.mainActivityIconsColors);
    }

    private void displayStartScreen (){

        if (getCurrentMoodFromPrefs(this).equals("5")){
            mViewPager.setCurrentItem(3);
            putCurrentMoodOnPrefs(this, "3");
        } else
            mViewPager.setCurrentItem(Integer.parseInt(getCurrentMoodFromPrefs(this)));
    }

    private void listeningViewPager(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {

                putCurrentMoodOnPrefs(getApplicationContext(),mViewPager.getCurrentItem()+"");
                updateMoodsAndNotesPrefs(getApplicationContext());
                printPrefsData(getApplicationContext());
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
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
    public static void displayNoteBox(final Context context){
        final EditText noteBox = new EditText(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage("Cancel to keep last note\nOk to delete last note");
        alert.setTitle("Note");
        alert.setView(noteBox);

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                noteBox.setText(null);
                printPrefsData(context);
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                putCurrentNoteOnPrefs(context, noteBox.getText().toString());
                putDayCounterOnPrefs(context,0);
                noteBox.setText(null);
                printPrefsData(context);
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(noteBox.getParent() != null)
            ((ViewGroup)noteBox.getParent()).removeView(noteBox);
        alert.show();
    }
}