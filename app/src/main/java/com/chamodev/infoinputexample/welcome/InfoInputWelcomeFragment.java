package com.chamodev.infoinputexample.welcome;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chamodev.infoinputexample.BaseFragment;
import com.chamodev.infoinputexample.survey.InfoInputSurveyActivity;
import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.util.CommonUtils;


public class InfoInputWelcomeFragment extends BaseFragment implements InfoInputWelcomeContract.View {

    private InfoInputWelcomeContract.Presenter mPresenter;

    private TextView mTitleTv;
    private TextView mSubTitleTv;
    private TextView mContentTv;
    private TextView mOkBtn;
    private ImageView mCoverIv;

    public static InfoInputWelcomeFragment newInstance() {
        InfoInputWelcomeFragment fragment = new InfoInputWelcomeFragment();
        return fragment;
    }


    public InfoInputWelcomeFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_input_welcome, container, false);

        mTitleTv = rootView.findViewById(R.id.info_input_welcome_title_tv);
        mSubTitleTv = rootView.findViewById(R.id.info_input_welcome_subtitle_tv);
        mContentTv = rootView.findViewById(R.id.info_input_welcome_content_tv);
        mOkBtn = rootView.findViewById(R.id.info_input_welcome_ok_btn);
        mCoverIv = rootView.findViewById(R.id.info_input_welcome_cover_iv);

        return rootView;
    }

    @Override
    public void setPresenter(InfoInputWelcomeContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void showWelcomeContent(boolean isFirstStep) {
        if (isFirstStep) {
            mTitleTv.setVisibility(View.VISIBLE);
            mSubTitleTv.setVisibility(View.VISIBLE);
            mOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInfoInputSurvey();
                }
            });
            mCoverIv.setImageResource(R.drawable.welcome_img);

            if (mPresenter.getTrainingCount() > 1){
                mTitleTv.setText(R.string.for_habit_molding);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.you_are_in_progress));
                mContentTv.setText(CommonUtils.substituteUserName(mActivity, R.string.you_need_some_repeat));
                mOkBtn.setText(R.string.ongoing_for_habit_molding);

            } else {
                mTitleTv.setText(R.string.starting_habit_molding);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.nice_to_meet_you));
                mContentTv.setText(CommonUtils.substituteUserName(mActivity, R.string.you_need_some_preparation));
                mOkBtn.setText(R.string.preparing_for_habit_molding);
            }

        } else {
            mTitleTv.setVisibility(View.VISIBLE);
            mSubTitleTv.setVisibility(View.GONE);
            mOkBtn.setText(R.string.habit_molding_start);
            mOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMainScreen();
                }
            });
            mCoverIv.setImageResource(R.drawable.compleat_img);

            if (mPresenter.getTrainingCount() > 1){
                mTitleTv.setText(R.string.ready_for_habit_molding_to_continue);
                mContentTv.setText(CommonUtils.substituteUserName(mActivity, R.string.start_with_new_mind));

            } else {
                mTitleTv.setText(R.string.ready_for_habit_molding);
                mContentTv.setText(CommonUtils.substituteUserName(mActivity, R.string.way_to_go_for_month));

            }
        }
    }

    @Override
    public void showMainScreen() {
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    @Override
    public void showInfoInputSurvey() {
        Intent intent = new Intent(mActivity, InfoInputSurveyActivity.class);
        startActivityForResult(intent, InfoInputWelcomeActivity.REQUEST_INFO_INPUT_SURVEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }
}
