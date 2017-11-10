package com.chamodev.infoinputexample.welcome;

import android.app.Activity;

import com.chamodev.infoinputexample.data.InfoInputSurveyRepository;

/**
 * Created by Koo on 2017. 11. 6..
 */

public class InfoInputWelcomePresenter implements InfoInputWelcomeContract.Presenter {
    private final InfoInputSurveyRepository mRepository;

    private final InfoInputWelcomeContract.View mView;

    private boolean mIsFirstStep = true;

    public InfoInputWelcomePresenter(InfoInputSurveyRepository repository,
                                     InfoInputWelcomeContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mIsFirstStep) {
            startInfoInput();
        } else {
            endInfoInput();
        }
    }


    @Override
    public void startInfoInput() {
        mView.showWelcomeContent(true);
    }

    @Override
    public void endInfoInput() {
        mView.showWelcomeContent(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (resultCode == Activity.RESULT_OK){
            mIsFirstStep = false;
        }

    }

    @Override
    public int getTrainingCount() {
        return mRepository.getTrainingCount();
    }
}
