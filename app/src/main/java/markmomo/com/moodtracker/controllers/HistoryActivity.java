package markmomo.com.moodtracker.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import markmomo.com.moodtracker.R;

import static markmomo.com.moodtracker.models.Preferences.getMoodsFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.getNotesFromPrefs;
import static markmomo.com.moodtracker.models.Preferences.putAppStatusOnPrefs;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<ImageButton> mButtons;
    private ArrayList<ConstraintLayout> mCstLayouts;
    private @ColorRes int mColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7;

        mButton1 = findViewById(R.id.act_history_day1_Btn); mButton2 = findViewById(R.id.act_history_day2_Btn);
        mButton3 = findViewById(R.id.act_history_day3_Btn); mButton4 = findViewById(R.id.act_history_day4_Btn);
        mButton5 = findViewById(R.id.act_history_day5_Btn); mButton6 = findViewById(R.id.act_history_day6_Btn);
        mButton7 = findViewById(R.id.act_history_day7_Btn);

        mButtons = new ArrayList<>();
        mButtons.add(mButton1); mButtons.add(mButton2); mButtons.add(mButton3); mButtons.add(mButton4);
        mButtons.add(mButton5); mButtons.add(mButton6); mButtons.add(mButton7);

        ConstraintLayout mDay1Left, mDay2Left, mDay3Left, mDay4Left, mDay5Left, mDay6Left, mDay7Left;

        mDay1Left = findViewById(R.id.act_history_day1); mDay2Left = findViewById(R.id.act_history_day2);
        mDay3Left = findViewById(R.id.act_history_day3); mDay4Left = findViewById(R.id.act_history_day4);
        mDay5Left = findViewById(R.id.act_history_day5); mDay6Left = findViewById(R.id.act_history_day6);
        mDay7Left = findViewById(R.id.act_history_day7);

        mCstLayouts = new ArrayList<>();
        mCstLayouts.add(mDay1Left); mCstLayouts.add(mDay2Left); mCstLayouts.add(mDay3Left); mCstLayouts.add(mDay4Left);
        mCstLayouts.add(mDay5Left); mCstLayouts.add(mDay6Left); mCstLayouts.add(mDay7Left);

        this.displayHistory();
        this.enableNoteIcons();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        putAppStatusOnPrefs(this,"inactive");
    }

    @Override
    protected void onStart() {
        super.onStart();

        putAppStatusOnPrefs(this,"started");
    }

    public void button7IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(6), Toast.LENGTH_SHORT).show();
    }
    public void button6IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(5), Toast.LENGTH_SHORT).show();
    }
    public void button5IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(4), Toast.LENGTH_SHORT).show();
    }
    public void button4IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(3), Toast.LENGTH_SHORT).show();
    }
    public void button3IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(2), Toast.LENGTH_SHORT).show();
    }
    public void button2IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(1), Toast.LENGTH_SHORT).show();
    }
    public void button1IsClicked(View view){
        Toast.makeText(this, getNotesFromPrefs(this).get(0), Toast.LENGTH_SHORT).show();
    }

    private void displayHistory () {
        int moodNumber;
        int position;
        int [] color = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65,
                R.color.light_sage, R.color.banana_yellow, R.color.noMoodsGrey};

        for (int i = 0; i < 7; i++){
            moodNumber = Integer.parseInt(getMoodsFromPrefs(this).get(i));
            position = i;
            mColor = color [moodNumber];
            chooseWidthAndColor(moodNumber, mCstLayouts.get(position), mButtons.get(position));
        }

        //display management of history colors, sizes, and icons.
//        ArrayList<String> moods = getMoodsFromPrefs(this);

//        for (int i = 0; i < moods.size(); i++) {
//            this.displayHistory(Integer.parseInt(moods.get(i)),i);
//        }
    }

    private int WidthOfScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();

        display.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    private void chooseWidthAndColor(int number , ConstraintLayout constraint, ImageButton imgButton) {

        switch (number){
            case 0 :
                constraint.setMaxWidth(WidthOfScreen()/5);
                break;
            case 1 :
                constraint.setMaxWidth((WidthOfScreen()/5)*2);
                break;
            case 2 :
                constraint.setMaxWidth((WidthOfScreen()/5)*3);
                break;
            case 3 :
                constraint.setMaxWidth((WidthOfScreen()/5)*4);
                break;
        }
        constraint.setBackgroundColor(getResources().getColor(mColor));
        imgButton.setBackgroundColor(getResources().getColor(mColor));
    }

    private void enableNoteIcons(){
        for (int i = 0; i < 7; i++){

            if (getNotesFromPrefs(this).get(i).equals("no note"))
                mButtons.get(i).setVisibility(View.GONE);
        }
    }
}