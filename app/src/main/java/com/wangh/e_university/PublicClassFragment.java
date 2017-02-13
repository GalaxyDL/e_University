package com.wangh.e_university;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wangh on 2016/8/22.
 */
public class PublicClassFragment extends ChoosingClassesFragment{

    @Override
    protected void updateView(){
        dataManager.updatePublicClasses(this);
    }


}
