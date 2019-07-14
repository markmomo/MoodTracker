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
    private static final String COMMENTS = "COMMENTS";
    private static final String CURRENT_COMMENT = "CURRENT_COMMENT";
    private static final String ACTIVITY_STATUS = "ACTIVITY_STATUS";
    private static SharedPreferences mPrefs;

    private static void declarePrefsService(Context context){

        mPrefs = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
    }

    //MOODS PREFS ---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------

    // obtiend l'humeur du jour enregistré dans les prefs
    public static String getCurrentMoodFromPrefs(Context context) {

        declarePrefsService(context);
        return mPrefs.getString(CURRENT_MOOD, "5");
    }

    // enregistre l'humeur du jour dans les prefs
    public static void putCurrentMoodInPrefs(Context context, String currentMood) {

        declarePrefsService(context);
        mPrefs.edit().putString(CURRENT_MOOD, currentMood).apply();
    }

    // obtiend la liste des humeurs enregistrés dans les prefs sous forme de String
    private static String getMoodsHistoryStringFromPrefs(Context context) {

        declarePrefsService(context);
        return mPrefs.getString(MOODS, "5");
    }

    // enregistre l'historique des humeurs dans les prefs
    private static void putMoodsHistoryStringInPrefs(String string){

        mPrefs.edit().putString(MOODS,string).apply();
    }

    // obtiend la liste des humeur enregistrés dans les prefs sous forme d'ArrayList<String>
    public static ArrayList<String> getMoodsHistoryArrayFromPrefs(Context context) {

        declarePrefsService(context);
        String moods = getMoodsHistoryStringFromPrefs(context);
        return new ArrayList<>(Arrays.asList(moods.split(",")));
    }

    //COMMENTS PREFS ---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------

    // obtiend la note du jour enregistré dans les prefs
    private static String getCurrentCommentFromPrefs(Context context) {

        declarePrefsService(context);
        return mPrefs.getString(CURRENT_COMMENT, "no comment");
    }

    // enregistre le commentaire du jour dans les prefs
    public static void putCurrentCommentInPrefs(Context context, String currentNote) {

        declarePrefsService(context);
        mPrefs.edit().putString(CURRENT_COMMENT, currentNote).apply();
    }

    // obtiend la liste des commentaires enregistrés dans les prefs sous forme de String
    private static String getCommentHistoryStringFromPrefs(Context context) {

        declarePrefsService(context);
        return mPrefs.getString(COMMENTS, "no comment");
    }

    // enregistre l'historique des commentaires dans les prefs
    private static void putCommentsHistoryStringInPrefs(String string){

        mPrefs.edit().putString(COMMENTS,string).apply();
    }

    // obtiend la liste des commentaires enregistrés dans les prefs sous forme d'ArrayList<String>
    public static ArrayList<String> getCommentsHistoryArrayFromPrefs(Context context) {

        declarePrefsService(context);
        String comments = getCommentHistoryStringFromPrefs(context);
        return new ArrayList<>(Arrays.asList(comments.split(",;,;;,;;")));
    }

    //MISCELLANEOUS ---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------

    // rempli les prefs MOODS et COMMENTS avec des valeurs par défaut si il n'existent pas.
    public static void nonNullHistoryPrefs(Context context) {

        declarePrefsService(context);
        if (getMoodsHistoryArrayFromPrefs(context).size() == 1)
            putMoodsHistoryStringInPrefs("5,5,5,5,5,5,5");
        if (getCommentsHistoryArrayFromPrefs(context).size() == 1)
            putCommentsHistoryStringInPrefs("no comment,;,;;,;;no comment,;,;;,;;no comment,;,;;,;;no comment,;,;;,;;no comment,;,;;,;;no comment,;,;;,;;no comment");
    }

    // enregistre Les entrées dans les humeurs et commentaires si le jour a changé et réinitialise les compteurs
    public static void buildMoodsAndCommentsHistoryInPrefs(Context context) {

        putMoodsHistoryStringInPrefs(getCurrentMoodFromPrefs(context)+","+ getMoodsHistoryStringFromPrefs(context));
        putCommentsHistoryStringInPrefs(getCurrentCommentFromPrefs(context)+",;,;;,;;"+ getCommentHistoryStringFromPrefs(context));
    }

    // réinitialise les humeurs, commentaires et compteur de jours par défaut
    public static void resetCurrentDataInPrefs (Context context){

        putCurrentMoodInPrefs(context,"5");
        putCurrentCommentInPrefs(context,"no comment");
    }

    // obtiend l'état de l'application enregistré dans les prefs (activité en cours)
    public static String getActivityStatusFromPrefs(Context context) {

        declarePrefsService(context);
        String appStatus;
        appStatus = mPrefs.getString(ACTIVITY_STATUS, "default");
        return appStatus;
    }

    // enregistre l'état de l'applicaiton dans les prefs (activité en cours)
    public static void putActivityStatusInPrefs(Context context, String appStatus) {

        declarePrefsService(context);
        mPrefs.edit().putString(ACTIVITY_STATUS, appStatus).apply();
    }

    public static void printDataFromPrefs(Context context){

        System.out.println("--------------------------------------------------\n");
        System.out.println("application is : " + getActivityStatusFromPrefs(context));
        System.out.println("current mood is : " + getCurrentMoodFromPrefs(context));
        System.out.println("moods history string is : " + getMoodsHistoryStringFromPrefs(context));
        System.out.println("moods history array is : " + getMoodsHistoryArrayFromPrefs(context));
        System.out.println("current note is : " + getCurrentCommentFromPrefs(context));
        System.out.println("comments history string is : " + getCommentHistoryStringFromPrefs(context));
        System.out.println("comments history array is : " + getCommentsHistoryArrayFromPrefs(context));
        System.out.println("--------------------------------------------------\n");
    }

    public static void resizePrefs(Context context){

        ArrayList<String> moodsArray;
        moodsArray = getMoodsHistoryArrayFromPrefs(context);
        ArrayList<String> commentsArray;
        commentsArray = getCommentsHistoryArrayFromPrefs(context);

        String moodsHistoryResized;
        String commentsHistoryResized;
        while(moodsArray.size() > 7){
            moodsArray.remove(moodsArray.size()-1);
        }
        while(commentsArray.size() > 7){
            commentsArray.remove(commentsArray.size()-1);
        }

        moodsHistoryResized = moodsArray.get(0) + ",";
        moodsHistoryResized += moodsArray.get(1) + ",";
        moodsHistoryResized += moodsArray.get(2) + ",";
        moodsHistoryResized += moodsArray.get(3) + ",";
        moodsHistoryResized += moodsArray.get(4) + ",";
        moodsHistoryResized += moodsArray.get(5) + ",";
        moodsHistoryResized += moodsArray.get(6);
        putMoodsHistoryStringInPrefs(moodsHistoryResized);

        commentsHistoryResized = commentsArray.get(0) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(1) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(2) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(3) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(4) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(5) + ",;,;;,;;";
        commentsHistoryResized += commentsArray.get(6);
        putCommentsHistoryStringInPrefs(commentsHistoryResized);
    }

    public static void deletePreferences (Context context){

        declarePrefsService(context);
        mPrefs.edit().clear().apply();
    }
}
