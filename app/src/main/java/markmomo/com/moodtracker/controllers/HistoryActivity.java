package markmomo.com.moodtracker.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import markmomo.com.moodtracker.R;

import static markmomo.com.moodtracker.models.Preferences.getCommentsHistoryArrayFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.getMoodsHistoryArrayFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.putActivityStatusInPrefs;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<ImageButton> mCommentsSavedButtons;
    private ArrayList<ConstraintLayout> mDaysCstLayouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton button1, button2, button3, button4, button5, button6, button7;

        button1 = findViewById(R.id.act_history_day1_Btn); button2 = findViewById(R.id.act_history_day2_Btn);
        button3 = findViewById(R.id.act_history_day3_Btn); button4 = findViewById(R.id.act_history_day4_Btn);
        button5 = findViewById(R.id.act_history_day5_Btn); button6 = findViewById(R.id.act_history_day6_Btn);
        button7 = findViewById(R.id.act_history_day7_Btn);

        mCommentsSavedButtons = new ArrayList<>();
        mCommentsSavedButtons.add(button1); mCommentsSavedButtons.add(button2); mCommentsSavedButtons.add(button3); mCommentsSavedButtons.add(button4);
        mCommentsSavedButtons.add(button5); mCommentsSavedButtons.add(button6); mCommentsSavedButtons.add(button7);

        ConstraintLayout mDay1Left, mDay2Left, mDay3Left, mDay4Left, mDay5Left, mDay6Left, mDay7Left;

        mDay1Left = findViewById(R.id.act_history_day1); mDay2Left = findViewById(R.id.act_history_day2);
        mDay3Left = findViewById(R.id.act_history_day3); mDay4Left = findViewById(R.id.act_history_day4);
        mDay5Left = findViewById(R.id.act_history_day5); mDay6Left = findViewById(R.id.act_history_day6);
        mDay7Left = findViewById(R.id.act_history_day7);

        mDaysCstLayouts = new ArrayList<>();
        mDaysCstLayouts.add(mDay1Left); mDaysCstLayouts.add(mDay2Left); mDaysCstLayouts.add(mDay3Left); mDaysCstLayouts.add(mDay4Left);
        mDaysCstLayouts.add(mDay5Left); mDaysCstLayouts.add(mDay6Left); mDaysCstLayouts.add(mDay7Left);

        this.displayHistory();
        this.enableCommentButtons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        putActivityStatusInPrefs(this,"history inactive");
    }

    @Override
    protected void onStart() {
        super.onStart();
        putActivityStatusInPrefs(this,"history");
    }

    public void button7IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(6), Toast.LENGTH_SHORT).show();
    }
    public void button6IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(5), Toast.LENGTH_SHORT).show();
    }
    public void button5IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(4), Toast.LENGTH_SHORT).show();
    }
    public void button4IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(3), Toast.LENGTH_SHORT).show();
    }
    public void button3IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(2), Toast.LENGTH_SHORT).show();
    }
    public void button2IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(1), Toast.LENGTH_SHORT).show();
    }
    public void button1IsClicked(View view){
        Toast.makeText(this, getCommentsHistoryArrayFromPrefs(this).get(0), Toast.LENGTH_SHORT).show();
    }

    private void displayHistory () {
        int moodNumber;
        int position;

        ArrayList<String> arrayList = getMoodsHistoryArrayFromPrefs(this);

        for (int i = 0; i < 7; i++){
            moodNumber = Integer.parseInt(arrayList.get(i));
            position = i;
            chooseWidthAndColor(moodNumber, mDaysCstLayouts.get(position), mCommentsSavedButtons.get(position));
        }
    }

    private int WidthOfScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();

        display.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    private void chooseWidthAndColor(int moodNumber , ConstraintLayout constraintLayout, ImageButton imgButton) {
        int [] color = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65,
                R.color.light_sage, R.color.banana_yellow, R.color.noMoodsGrey};

        switch (moodNumber){
            case 0 :
                constraintLayout.setMaxWidth(WidthOfScreen()/5);
                break;
            case 1 :
                constraintLayout.setMaxWidth((WidthOfScreen()/5)*2);
                break;
            case 2 :
                constraintLayout.setMaxWidth((WidthOfScreen()/5)*3);
                break;
            case 3 :
                constraintLayout.setMaxWidth((WidthOfScreen()/5)*4);
                break;
        }
        constraintLayout.setBackgroundColor(getResources().getColor(color[moodNumber]));
        imgButton.setBackgroundColor(getResources().getColor(color[moodNumber]));
    }

    private void enableCommentButtons(){
        for (int i = 0; i < 7; i++){

            if (getCommentsHistoryArrayFromPrefs(this).get(i).equals("no comment"))
                mCommentsSavedButtons.get(i).setVisibility(View.GONE);
        }
    }
}