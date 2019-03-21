package markmomo.com.moodtracker.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

import static markmomo.com.moodtracker.models.UserMoods.DAY_COUNTER_MOODS;
import static markmomo.com.moodtracker.models.UserNotes.DAY_COUNTER_NOTES;


/**
 * Created by markm On 20/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs;
        //increment DAY_COUNTER by 1
        prefs = context.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);

        prefs.edit().putInt(DAY_COUNTER_MOODS, prefs.getInt(DAY_COUNTER_MOODS ,0)+1).apply();
        prefs.edit().putInt(DAY_COUNTER_NOTES, prefs.getInt(DAY_COUNTER_NOTES ,0)+1).apply();

        Toast.makeText(context,"dayCounter = "+prefs.getInt(DAY_COUNTER_MOODS,0),Toast.LENGTH_SHORT).show();
        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
    }
}