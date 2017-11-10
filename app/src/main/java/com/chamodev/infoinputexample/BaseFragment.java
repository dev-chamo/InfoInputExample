package com.chamodev.infoinputexample;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * Created by Koo on 2017. 11. 6..
 */

public class BaseFragment extends Fragment {
    public Activity mActivity;

    private Dialog mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mActivity = getActivity();
    }

    public void showMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    public void showLoadingView() {
        if (mActivity != null) {
            mLoadingDialog = new Dialog(mActivity, R.style.LoadingDialog);
            ProgressBar progressBar = new ProgressBar(mActivity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mLoadingDialog.addContentView(progressBar, params);
            mLoadingDialog.setCancelable(false);
        }
    }

    public void hideLoadingView() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
