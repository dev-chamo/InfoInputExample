package com.chamodev.infoinputexample.survey;

import android.app.TimePickerDialog;

import com.chamodev.infoinputexample.BasePresenter;
import com.chamodev.infoinputexample.BaseView;
import com.chamodev.infoinputexample.data.InfoInputSurvey;
import com.chamodev.infoinputexample.data.InfoInputSurveyType;

/**
 * Created by Koo on 2017. 11. 6..
 */

public interface InfoInputSurveyContract {
    interface View extends BaseView<Presenter> {
        void showLoadingView();

        void hideLoadingView();

        void showMyKeywordSurvey(InfoInputSurvey.SurveyQuestion currentWorryQuestion,
                                 InfoInputSurvey.SurveyQuestion eatingHabitsQuestion);

        void showSpecialMissionSurvey(InfoInputSurvey.SurveyQuestion specialMissionQuestion);

        void showHabitMissionSurvey(InfoInputSurvey.SurveyQuestion habitMissionQuestion);

        void showMealTimeAlarmSurvey(boolean isAllChecked, String[] alarmTimeArray);

        void showWorkoutTimeAlarmSurvey(boolean isAllChecked, String[] alarmTimeArray);

        void showAboutMeSurvey(String aboutMe);

        void hideSurvey(InfoInputSurveyType surveyType);

        void showInputInfoWelcomePage(boolean isComplete);

        void setSurveyHeader(InfoInputSurveyType surveyType);

        void setNextButtonEnabled(boolean enabled);

        void showTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener, int hour, int minute);
    }

    interface Presenter extends BasePresenter {
        void getInfoInputSurvey();

        void postInfoInputSurvey();

        void setMealTimeAlarm(String[] timeArray);

        void setWorkoutTimeAlarm(String[] timeArray);

        void setAboutMe(String aboutMe);

        void goToNextStep();

        void goToPreviousStep();

        int getTrainingCount();
    }
}
