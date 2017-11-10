package com.chamodev.infoinputexample.welcome;

import com.chamodev.infoinputexample.BasePresenter;
import com.chamodev.infoinputexample.BaseView;
import com.chamodev.infoinputexample.data.InfoInputSurvey;

/**
 * Created by Koo on 2017. 11. 6..
 */

public interface InfoInputWelcomeContract {
    interface View extends BaseView<Presenter> {
        void showLoadingView();

        void hideLoadingView();

        void showWelcomeContent(boolean isFirstStep);

        void showMainScreen();

        void showInfoInputSurvey();

    }

    interface Presenter extends BasePresenter {
        void startInfoInput();

        void endInfoInput();

        void result(int requestCode, int resultCode);

        int getTrainingCount();
    }
}
