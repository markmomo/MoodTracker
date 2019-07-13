package markmomo.com.moodtracker.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import markmomo.com.moodtracker.controllers.HistoryActivity;

import static markmomo.com.moodtracker.models.Preferences.getAppStatusFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.getDayCounterFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.printPrefsData;
import static markmomo.com.moodtracker.models.Preferences.putDayCounterOnPrefs;
import static markmomo.com.moodtracker.models.Preferences.updateMoodsAndNotesPrefs;

/**
 * Created by markm On 20/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        intent = new Intent(context, HistoryActivity.class);
        if (getAppStatusFromPrefs(context).equals("started")){
            context.startActivity(intent);
        }

        printPrefsData(context);
        putDayCounterOnPrefs(context,getDayCounterFromPrefs(context)+1);
        updateMoodsAndNotesPrefs(context);

        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
    }
}