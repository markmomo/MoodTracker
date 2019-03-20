package markmomo.com.moodtracker.tools;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import markmomo.com.moodtracker.models.SmileysFragment;

/**
 * Created by markm On 20/03/2019.
 */
public class SmileysAdapter extends FragmentPagerAdapter {

    private int[] mColors;
    public int mainActivityIconsColors;


    public SmileysAdapter(FragmentManager mgr, int[] colors) {
        super(mgr);
        this.mColors = colors;
    }

    @Override
    public int getCount() {
        return(5);
    }

    @Override
    public Fragment getItem(int position) {

        mainActivityIconsColors = position;

        return(SmileysFragment.newInstance(position, this.mColors[position]));
    }
}