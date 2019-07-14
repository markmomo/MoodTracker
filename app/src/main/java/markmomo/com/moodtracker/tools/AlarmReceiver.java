package markmomo.com.moodtracker.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import markmomo.com.moodtracker.controllers.HistoryActivity;

import static markmomo.com.moodtracker.models.Preferences.buildMoodsAndCommentsHistoryInPrefs;
import static markmomo.com.moodtracker.models.Preferences.getActivityStatusFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.printDataFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.resetCurrentDataInPrefs;
import static markmomo.com.moodtracker.models.Preferences.resizePrefs;

/**
 * Created by markm On 20/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
        printDataFromPrefs(context);
        buildMoodsAndCommentsHistoryInPrefs(context);
        resetCurrentDataInPrefs (context);
        resizePrefs(context);

        intent = new Intent(context, HistoryActivity.class);
        if (getActivityStatusFromPrefs(context).equals("history")){
            context.startActivity(intent);
        }
        System.out.println("onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
        printDataFromPrefs(context);
        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
    }
}