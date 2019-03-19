package markmomo.com.moodtracker.models;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import markmomo.com.moodtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmileysFragment extends Fragment {


    public SmileysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smileys, container, false);
    }

}
