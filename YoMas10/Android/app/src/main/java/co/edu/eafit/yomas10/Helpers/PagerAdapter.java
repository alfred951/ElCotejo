package co.edu.eafit.yomas10.Helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jalvar53 on 10/4/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0){
        switch (arg0){
            case 0:
                return new Frag1();
            case 1:
                return new Frag2();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount(){
        return 2;
    }
}
