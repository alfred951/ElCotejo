package co.edu.eafit.yomas10.Helpers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.eafit.yomas10.R;

/**
 * Created by jalvar53 on 10/4/15.
 */
public class Frag1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_one, container, false);
    }
}
