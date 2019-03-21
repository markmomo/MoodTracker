package markmomo.com.moodtracker.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by markm On 20/03/2019.
 */
public class UserMoods {

    private Context mContext;
    private List<String> mMoods;

    public static final String MOODS = "MOODS";
    public static final String DAY_COUNTER_MOODS = "DAY_COUNTER_MOODS";
    public static final String CURRENT_MOOD = "CURRENT_MOOD";


    public UserMoods(Context context) {
        mContext = context;
    }

    public List<String> getMoods() {
        return mMoods;
    }

    public void updateMoodsData(){
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        //initialize mMoods if empty
        if (mMoods == null || mMoods.isEmpty()) {
            mMoods = new ArrayList<>();
            mMoods.add(0,"3");
            while (mMoods.size() != 8){
                mMoods.add("-1");
            }
        }
        //keep mMoods ArrayList size to 8 items
        while (mMoods.size() > 8){
            mMoods.remove(mMoods.size()-1);
        }
        // add empty entries on missing days and reset DAY_COUNTER_MOODS
        if (prefs.getInt(DAY_COUNTER_MOODS,0)>0){
            for(int i = 1;i < prefs.getInt(DAY_COUNTER_MOODS,0);i++){
                mMoods.add(0,"-1");
            }
            prefs.edit().putInt(DAY_COUNTER_MOODS,0).apply();
            mMoods.add(0,prefs.getInt(CURRENT_MOOD,-1)+"");

            //update today entry
        }else {
            mMoods.remove(0);
            mMoods.add(0,prefs.getInt(CURRENT_MOOD,-1)+"");
        }
    }

    public void saveMoodsData() {
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        StringBuilder moodsString = new StringBuilder();
        for (String s : mMoods){
            moodsString.append(s);
            moodsString.append(",");
        }
        mMoods.clear();

        prefs.edit().putString(MOODS, moodsString.toString()).apply();
    }

    public void loadMoodsData() {
        SharedPreferences prefs = mContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String moods = prefs.getString("MOODS", null);

        mMoods = new ArrayList<>(Arrays.asList(moods.split(",")));
    }
}
