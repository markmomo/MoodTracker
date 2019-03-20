package markmomo.com.moodtracker.controllers;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import markmomo.com.moodtracker.R;
import markmomo.com.moodtracker.tools.SmileysAdapter;


public class MainActivity extends AppCompatActivity {

    ImageButton mNoteIcon,mHistoryIcon;


    public void historyIconIsClicked (View view){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);

        this.configureViewPager();
    }
    private void configureViewPager(){

        ViewPager pager = findViewById(R.id.act_main_view_pager);

        SmileysAdapter smileysAdapter;
        smileysAdapter = new SmileysAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        pager.setAdapter(smileysAdapter);

        mNoteIcon.setBackgroundColor(smileysAdapter.mainActivityIconsColors);
        mHistoryIcon.setBackgroundColor(smileysAdapter.mainActivityIconsColors);
    }
}