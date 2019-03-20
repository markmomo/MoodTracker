package markmomo.com.moodtracker.tools;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;



/**
 * Created by markm On 20/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final String DAY_COUNTER = "DAY_COUNTER";


    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs;
        //increment DAY_COUNTER by 1
        prefs = context.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        prefs.edit().putInt(DAY_COUNTER, prefs.getInt(DAY_COUNTER ,0)+1).apply();

        Toast.makeText(context,"dayCounter = "+prefs.getInt(DAY_COUNTER,0),Toast.LENGTH_SHORT).show();
        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");
    }
}