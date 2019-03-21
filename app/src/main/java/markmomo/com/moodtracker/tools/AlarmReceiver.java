package markmomo.com.moodtracker.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

import static markmomo.com.moodtracker.models.UserMoods.CURRENT_MOOD;
import static markmomo.com.moodtracker.models.UserMoods.MOODS;
import static markmomo.com.moodtracker.models.UserNotes.CURRENT_NOTES;
import static markmomo.com.moodtracker.models.UserNotes.NOTES;

/**
 * Created by markm On 20/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {

    SharedPreferences mPrefs;
    public static final String DAY_COUNTER = "DAY_COUNTER";

    @Override
    public void onReceive(Context context, Intent intent) {

        mPrefs = context.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

        //increment DAY_COUNTER by 1
        mPrefs.edit().putInt(DAY_COUNTER, mPrefs.getInt(DAY_COUNTER ,0)+1).apply();

        updateMoods();
        updateNotes();

        Toast.makeText(context,"dayCounter = "+mPrefs.getInt(DAY_COUNTER,0),Toast.LENGTH_SHORT).show();
        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
    }

    public void updateMoods(){

        if (mPrefs.getInt(DAY_COUNTER,0) > 1){

            mPrefs.edit().putString(MOODS,"-1"+ "," + mPrefs.getString(MOODS,"-1")).apply();
            mPrefs.edit().putString(CURRENT_MOOD,"-1").apply();
            System.out.println(mPrefs.getString(MOODS,"-1"));

        } else if (mPrefs.getInt(DAY_COUNTER,0) == 1)  {

            mPrefs.edit().putString(MOODS, mPrefs.getString(CURRENT_MOOD,"-1") + "," + mPrefs.getString(MOODS,"-1")).apply();
            mPrefs.edit().putString(CURRENT_MOOD,"-1").apply();
            System.out.println(mPrefs.getString(MOODS,"-1"));
        }
    }

    public void updateNotes(){

        if (mPrefs.getInt(DAY_COUNTER,0) > 1){

            mPrefs.edit().putString(NOTES,"no note"+ ",;,;;,;;" + mPrefs.getString(NOTES,"no note")).apply();
            mPrefs.edit().putString(CURRENT_NOTES,"no note").apply();
            System.out.println(mPrefs.getString(NOTES,"no note"));

        } else if (mPrefs.getInt(DAY_COUNTER,0) == 1)  {

            mPrefs.edit().putString(NOTES, mPrefs.getString(CURRENT_NOTES,"no note") + ",;,;;,;;" + mPrefs.getString(NOTES,"no note")).apply();
            mPrefs.edit().putString(CURRENT_NOTES,"no note").apply();
            System.out.println(mPrefs.getString(NOTES,"no note"));
        }
    }
}