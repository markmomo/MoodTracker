package markmomo.com.moodtracker.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by markm On 08/04/2019.
 */
public class Preferences {

    private static final String MOODS = "MOODS";
    private static final String CURRENT_MOOD = "CURRENT_MOOD";
    private static final String NOTES = "NOTES";
    private static final String CURRENT_NOTE = "CURRENT_NOTE";
    private static final String DAY_COUNTER = "DAY_COUNTER";
    private static final String APP_STATUS = "APP_STATUS";

    private static String mCurrentMood;
    private static String mCurrentNote;
    private static int mDayCounter;

    private static SharedPreferences mPrefs;


    public static void initializePrefs(Context context) {

        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (mPrefs.getString(MOODS,null) == null)
            mPrefs.edit().putString(MOODS,"5,5,5,5,5,5,5").apply();
        if (mPrefs.getString(NOTES,null) == null)
            mPrefs.edit().putString(NOTES,"no note,;,;;,;;no note,;,;;,;;no note,;,;;,;;no note,;,;;,;;no note,;,;;,;;no note,;,;;,;;no note").apply();
    }

    public static String getAppStatusFromPrefs(Context context) {
        String appStatus;
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        appStatus = mPrefs.getString(APP_STATUS, "default");
        return appStatus;
    }

    public static void putAppStatusOnPrefs(Context context, String appStatus) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mPrefs.edit().putString(APP_STATUS, appStatus).apply();
    }

    public static String getCurrentMoodFromPrefs(Context context) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mCurrentMood = mPrefs.getString(CURRENT_MOOD, "5");
        return mCurrentMood;
    }

    private static String getCurrentNoteFromPrefs(Context context) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mCurrentNote = mPrefs.getString(CURRENT_NOTE, "no note");
        return mCurrentNote;
    }

    public static int getDayCounterFromPrefs(Context context) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mDayCounter = mPrefs.getInt(DAY_COUNTER, 0);
        return mDayCounter;
    }

    public static ArrayList<String> getMoodsFromPrefs(Context context) {
        ArrayList<String> moodsHistory;
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String moods = mPrefs.getString(MOODS, "5");
        moodsHistory = new ArrayList<>(Arrays.asList(moods.split(",")));
        return moodsHistory;
    }

    public static ArrayList<String> getNotesFromPrefs(Context context) {
        ArrayList<String> notesHistory;
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String notes = mPrefs.getString(NOTES, "no note");
        notesHistory = new ArrayList<>(Arrays.asList(notes.split(",;,;;,;;")));
        return notesHistory;
    }

    private static String getMoodsStringFromPrefs(Context context) {
        String moodsString;
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        moodsString = mPrefs.getString(MOODS, "5");
        return moodsString;
    }

    private static String getNotesStringFromPrefs(Context context) {
        String notesString;
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        notesString = mPrefs.getString(NOTES, "no note");
        return notesString;
    }

    public static void putCurrentMoodOnPrefs(Context context, String currentMood) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mPrefs.edit().putString(CURRENT_MOOD, currentMood).apply();
        mCurrentMood = currentMood;
    }

    public static void putCurrentNoteOnPrefs(Context context, String currentNote) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mPrefs.edit().putString(CURRENT_NOTE, currentNote).apply();
        mCurrentNote = currentNote;
    }

    public static void putDayCounterOnPrefs(Context context, int currentDay) {
        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        mPrefs.edit().putInt(DAY_COUNTER, currentDay).apply();
        mDayCounter = currentDay;
    }


    public static void updateMoodsAndNotesPrefs(Context context) {

        if (getDayCounterFromPrefs(context) > 1){
            buildMoodsHistoryString("5"+","+getMoodsStringFromPrefs(context));
            buildNotesHistoryString("no note"+",;,;;,;;"+getNotesStringFromPrefs(context));
            resetPrefsCurrentData(context);

        } else if (getDayCounterFromPrefs(context) == 1)  {
            buildMoodsHistoryString(getCurrentMoodFromPrefs(context)+","+ getMoodsStringFromPrefs(context));
            buildNotesHistoryString(getCurrentNoteFromPrefs(context)+",;,;;,;;"+ getNotesStringFromPrefs(context));
            resetPrefsCurrentData(context);
        }
    }
    private static void buildMoodsHistoryString(String string){
        mPrefs.edit().putString(MOODS,string).apply();
    }

    private static void buildNotesHistoryString(String string){
        mPrefs.edit().putString(NOTES,string).apply();
    }

    private static void resetPrefsCurrentData (Context context){
        putCurrentMoodOnPrefs(context,"5");
        putCurrentNoteOnPrefs(context,"no note");
        putDayCounterOnPrefs(context,0);
    }

    public static void printPrefsData(Context context){
        System.out.println("--------------------------------------------------\n");
        System.out.println("application is : " + getAppStatusFromPrefs(context));
        System.out.println("current mood is : " + getCurrentMoodFromPrefs(context));
        System.out.println("moods history is : " + getMoodsFromPrefs(context));
        System.out.println("current note is : " + getCurrentNoteFromPrefs(context));
        System.out.println("notes history is : " + getNotesFromPrefs(context));
        System.out.println("--------------------------------------------------\n");
    }
}