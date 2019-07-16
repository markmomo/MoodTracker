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
import static markmomo.com.moodtracker.models.Preferences.fillHistoryPrefsIfEmpty;
import static markmomo.com.moodtracker.models.Preferences.printDataFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.putCurrentCommentInPrefs;
import static markmomo.com.moodtracker.models.Preferences.putCurrentMoodInPrefs;

public class MainActivity extends AppCompatActivity {

    private ImageButton mCommentIcon,mHistoryIcon;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCommentIcon = findViewById(R.id.act_main_comment_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);

        fillHistoryPrefsIfEmpty(this);
        this.configureViewPager();
        this.displayStartScreen();
        this.listeningViewPager();
        this.startAlarm();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.configureViewPager();
        this.displayStartScreen();
        this.listeningViewPager();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //deletePreferences(this);
    }

    public void historyIconIsClicked (View view){

        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    public void commentIconIsClicked (View view) {

        displayCommentBox(this);
    }

    private void configureViewPager(){

        mViewPager = findViewById(R.id.act_main_view_pager);
        MoodsAdapter moodsAdapter = new MoodsAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        mViewPager.setAdapter(moodsAdapter);
        mCommentIcon.setBackgroundColor(moodsAdapter.mainActivityIconsColors);
        mHistoryIcon.setBackgroundColor(moodsAdapter.mainActivityIconsColors);
    }

    private void displayStartScreen (){

        if (getCurrentMoodFromPrefs(this).equals("5")){
            mViewPager.setCurrentItem(3);
            putCurrentMoodInPrefs(this, "3");
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

                putCurrentMoodInPrefs(getApplicationContext(),mViewPager.getCurrentItem()+"");
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
    public static void displayCommentBox(final Context context){

        final EditText commentBox = new EditText(context);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setMessage("Cancel to keep last note\nOk to delete last note");
        alert.setTitle("comment");
        alert.setView(commentBox);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                commentBox.setText(null);
                printDataFromPrefs(context);
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                putCurrentCommentInPrefs(context, commentBox.getText().toString());
                commentBox.setText(null);
                printDataFromPrefs(context);
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(commentBox.getParent() != null)
            ((ViewGroup)commentBox.getParent()).removeView(commentBox);
        alert.show();
    }
}