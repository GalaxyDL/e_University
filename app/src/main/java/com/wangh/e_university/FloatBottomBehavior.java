package com.wangh.e_university;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangh on 2016/10/6.
 */

public class FloatBottomBehavior extends CoordinatorLayout.Behavior{

    public FloatBottomBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes& ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if(dy>0&&child.getPaddingBottom()>0){
            child.setPadding(0,0,0,0);
        }
        if(dx<0&&child.getPaddingBottom()==0){
            child.setPadding(0,0,0,60);
        }
    }
}
