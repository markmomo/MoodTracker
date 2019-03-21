package markmomo.com.moodtracker.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by markm On 20/03/2019.
 */
public class UserMoods {

    private Context mContext;

    public static final String MOODS = "MOODS";
    public static final String CURRENT_MOOD = "CURRENT_MOOD";


    public UserMoods(Context context) {
        mContext = context;
    }

    public ArrayList<String> getMoods() {

        SharedPreferences prefs;
        prefs = mContext.getSharedPreferences("myPrefs", MODE_PRIVATE);
        ArrayList<String> mMoods;
        String moods = prefs.getString(MOODS, "-1");
        mMoods = new ArrayList<>(Arrays.asList(moods.split(",")));

        return mMoods;
    }
}