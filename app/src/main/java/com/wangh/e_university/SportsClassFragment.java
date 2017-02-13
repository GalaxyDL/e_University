package com.wangh.e_university;

import android.support.v4.app.Fragment;

/**
 * Created by wangh on 2016/8/22.
 */
public class SportsClassFragment extends ChoosingClassesFragment {
    @Override
    protected void updateView() {
        dataManager.updateSportsClasses(this);
    }
}
