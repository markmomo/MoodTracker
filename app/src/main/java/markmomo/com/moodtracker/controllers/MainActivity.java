package markmomo.com.moodtracker.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.SystemClock;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import java.util.Calendar;

import markmomo.com.moodtracker.R;
import markmomo.com.moodtracker.models.UserMoods;
import markmomo.com.moodtracker.tools.AlarmReceiver;
import markmomo.com.moodtracker.tools.SmileysAdapter;

import static markmomo.com.moodtracker.models.UserMoods.CURRENT_MOOD;


public class MainActivity extends AppCompatActivity {

    UserMoods mUserMoods;
    ImageButton mNoteIcon,mHistoryIcon;
    ViewPager mPager;


    public void historyIconIsClicked (View view){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);
        mUserMoods = new UserMoods(this);

        mUserMoods.loadMoodsData();
        configureViewPager();
        startAlarm ();
        listeningViewPager();
    }


    @Override
    protected void onStop() {
        super.onStop();

        mUserMoods.updateMoodsData();
        mUserMoods.saveMoodsData();
        mUserMoods.loadMoodsData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUserMoods.updateMoodsData();
        mUserMoods.saveMoodsData();
        mUserMoods.loadMoodsData();
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

                mUserMoods.updateMoodsData();
                mUserMoods.saveMoodsData();
                mUserMoods.loadMoodsData();
                System.out.println(mUserMoods.getMoods());

            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

}