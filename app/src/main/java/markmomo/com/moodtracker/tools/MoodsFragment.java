package markmomo.com.moodtracker.tools;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import markmomo.com.moodtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoodsFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private static final String KEY_COLOR = "color";
    private @DrawableRes
    int smileys[] = new int[]{R.drawable.smiley_sad, R.drawable.smiley_disappointed,
            R.drawable.smiley_normal, R.drawable.smiley_happy,
            R.drawable.smiley_super_happy};

    public MoodsFragment() {
    }

    public static MoodsFragment newInstance(int position, int color) {

        MoodsFragment frag = new MoodsFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        frag.setArguments(args);
        return (frag);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_smileys, container, false);
        LinearLayout rootView = result.findViewById(R.id.frag_smiley_root);
        ImageView imageView = result.findViewById(R.id.frag_smiley);

        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        rootView.setBackgroundColor(color);

        for (int i = 0; i < position + 1; i++) {
            imageView.setImageResource(smileys[i]);
        }
            Log.e(getClass().getSimpleName(), "onCreateView called for fragment number " + position);
            return result;
    }
}
