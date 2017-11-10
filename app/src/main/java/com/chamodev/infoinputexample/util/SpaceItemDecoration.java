package com.chamodev.infoinputexample.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Koo on 16. 4. 1..
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mTopMargin = 0;
    private int mSpaceMargin;
    private boolean mHasGridLayout;

    public SpaceItemDecoration(int margin, boolean hasGridLayout) {
        mSpaceMargin = margin;
        mHasGridLayout = hasGridLayout;
    }

    public void setTopMargin(int topMargin) {
        mTopMargin = topMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mHasGridLayout) {
            outRect.top = mSpaceMargin;
            outRect.bottom = mSpaceMargin;
            outRect.left = mSpaceMargin;
            outRect.right = mSpaceMargin;

        } else {
            outRect.bottom = mSpaceMargin;
        }

        int position = parent.getChildAdapterPosition(view);
        if (position == 0 && mTopMargin > 0) {
            outRect.top = mTopMargin;
        }

        ViewGroup child = (ViewGroup) parent.getChildAt(position);

        if (child != null && child.getChildCount() == 0) {
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.left = 0;
            outRect.right = 0;
        }
    }
}
