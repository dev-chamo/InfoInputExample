package com.chamodev.infoinputexample.survey;

import android.support.annotation.NonNull;

import com.chamodev.infoinputexample.BuildConfig;
import com.chamodev.infoinputexample.data.InfoInputSurvey;
import com.chamodev.infoinputexample.data.InfoInputSurveyDataSource;
import com.chamodev.infoinputexample.data.InfoInputSurveyRepository;
import com.chamodev.infoinputexample.data.InfoInputSurveyType;
import com.chamodev.infoinputexample.util.CommonUtils;


/**
 * Created by Koo on 2017. 11. 6..
 */

public class InfoInputSurveyPresenter implements InfoInputSurveyContract.Presenter {

    private final InfoInputSurveyRepository mRepository;

    private final InfoInputSurveyContract.View mView;

    private InfoInputSurvey mInfoInputSurvey;

    private int mCurrentStep = 0;

    private boolean mHasMealTimeAlarm = false;
    private boolean mHasWorkoutTimeAlarm = false;

    public InfoInputSurveyPresenter(@NonNull InfoInputSurveyRepository repository,
                                    @NonNull InfoInputSurveyContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mInfoInputSurvey == null) {
            if (mCurrentStep == 0) {
                getInfoInputSurvey();
            } else {
                mView.showInputInfoWelcomePage(false);
            }
        }
    }

    @Override
    public void getInfoInputSurvey() {
        mView.showLoadingView();

        mRepository.getInfoInputSurvey(new InfoInputSurveyDataSource.LoadInfoInputSurveyCallback() {
            @Override
            public void onInfoInputSurveyLoaded(InfoInputSurvey infoInputSurvey) {
                mInfoInputSurvey = infoInputSurvey;
                goToNextStep();
                mView.hideLoadingView();
            }

            @Override
            public void onDataNotAvailable() {
                mView.hideLoadingView();
            }
        });
    }

    @Override
    public void postInfoInputSurvey() {
        mView.showLoadingView();

        mRepository.postInfoInputSurvey(mInfoInputSurvey, new InfoInputSurveyDataSource.SaveInfoInputSurveyCallback() {
            @Override
            public void onDataSaved() {
                mView.hideLoadingView();
                mView.showInputInfoWelcomePage(true);
            }

            @Override
            public void onDataNotSaved() {
                mView.hideLoadingView();
            }
        });

    }

    @Override
    public void setMealTimeAlarm(String[] timeArray) {
        mInfoInputSurvey.setMealTimeArray(timeArray);
        mHasMealTimeAlarm = true;
    }

    @Override
    public void setWorkoutTimeAlarm(String[] timeArray) {
        mInfoInputSurvey.setWorkoutTimeArray(timeArray);
        mHasWorkoutTimeAlarm = true;
    }

    @Override
    public void setAboutMe(String aboutMe) {
        mInfoInputSurvey.setAboutMe(aboutMe);
    }


    @Override
    public void goToNextStep() {
        CommonUtils.hideSoftKeyboard(((InfoInputSurveyFragment) mView).mActivity);

        mView.hideSurvey(InfoInputSurveyType.values()[mCurrentStep]);

        mView.setSurveyHeader(InfoInputSurveyType.values()[++mCurrentStep]);

        switch (InfoInputSurveyType.values()[mCurrentStep]) {
            case BASIC_INFO:
                mView.showMyKeywordSurvey(mInfoInputSurvey.getCurrentWorryQuestion(),
                        mInfoInputSurvey.getEatingHabitsQuestion());
                break;

            case SPECIAL_MISSION:
                mView.showSpecialMissionSurvey(mInfoInputSurvey.getSpecialMissionQuestion());
                break;

            case HABIT_MISSION:
                mView.showHabitMissionSurvey(mInfoInputSurvey.getHabitMissionQuestion());
                break;

            case MEAL_TIME_ALARM:
                String[] mealAlarmTimeArray = mInfoInputSurvey.getMealTimeArray();
                if (mealAlarmTimeArray == null) {
                    mealAlarmTimeArray = new String[]{"0800", "1200", "1800"};
                }
                if (getTrainingCount() > 1){
                    mHasMealTimeAlarm = true;
                }
                mView.showMealTimeAlarmSurvey(mHasMealTimeAlarm, mealAlarmTimeArray);
                break;

            case WORKOUT_TIME_ALARM:
                String[] workoutAlarmTimeArray = mInfoInputSurvey.getWorkoutTimeArray();
                if (workoutAlarmTimeArray == null) {
                    workoutAlarmTimeArray = new String[]{"0700", "0700", "0700", "0700", "0700", "0700", "0700"};
                }
                if (getTrainingCount() > 1){
                    mHasWorkoutTimeAlarm = true;
                }
                mView.showWorkoutTimeAlarmSurvey(mHasWorkoutTimeAlarm, workoutAlarmTimeArray);
                break;

            case ABOUT_ME:
                mView.showAboutMeSurvey(mInfoInputSurvey.getAboutMe());
                break;

            case END:
                postInfoInputSurvey();
                break;
        }
    }

    @Override
    public void goToPreviousStep() {
        CommonUtils.hideSoftKeyboard(((InfoInputSurveyFragment) mView).mActivity);

        mView.hideSurvey(InfoInputSurveyType.values()[mCurrentStep]);

        mView.setSurveyHeader(InfoInputSurveyType.values()[--mCurrentStep]);

        switch (InfoInputSurveyType.values()[mCurrentStep]) {
            case START:
                mView.showInputInfoWelcomePage(false);
                break;

            case BASIC_INFO:
                mView.showMyKeywordSurvey(mInfoInputSurvey.getCurrentWorryQuestion(),
                        mInfoInputSurvey.getEatingHabitsQuestion());
                break;

            case SPECIAL_MISSION:
                mView.showSpecialMissionSurvey(mInfoInputSurvey.getSpecialMissionQuestion());
                break;

            case HABIT_MISSION:
                mView.showHabitMissionSurvey(mInfoInputSurvey.getHabitMissionQuestion());
                break;

            case MEAL_TIME_ALARM:
                String[] mealAlarmTimeArray = mInfoInputSurvey.getMealTimeArray();
                if (mealAlarmTimeArray == null) {
                    mealAlarmTimeArray = new String[]{"0800", "1200", "1800"};
                }
                if (getTrainingCount() > 1){
                    mHasMealTimeAlarm = true;
                }

                mView.showMealTimeAlarmSurvey(mHasMealTimeAlarm, mealAlarmTimeArray);
                break;

            case WORKOUT_TIME_ALARM:
                String[] workoutAlarmTimeArray = mInfoInputSurvey.getWorkoutTimeArray();
                if (workoutAlarmTimeArray == null) {
                    workoutAlarmTimeArray = new String[]{"0700", "0700", "0700", "0700", "0700", "0700", "0700"};
                }
                if (getTrainingCount() > 1){
                    mHasWorkoutTimeAlarm = true;
                }

                mView.showWorkoutTimeAlarmSurvey(mHasWorkoutTimeAlarm, workoutAlarmTimeArray);
                break;

            case ABOUT_ME:
                mView.showAboutMeSurvey(mInfoInputSurvey.getAboutMe());
                break;
        }
    }

    @Override
    public int getTrainingCount() {
        return mRepository.getTrainingCount();
    }
}
