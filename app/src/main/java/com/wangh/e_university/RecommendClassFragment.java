package com.wangh.e_university;

import android.support.v4.app.Fragment;

/**
 * Created by wangh on 2016/8/22.
 */
public class RecommendClassFragment extends ChoosingClassesFragment {

    @Override
    protected void updateView() {
        dataManager.updateRecommendClasses(this);
    }
}
