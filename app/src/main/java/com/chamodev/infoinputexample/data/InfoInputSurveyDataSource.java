package com.chamodev.infoinputexample.data;

import android.support.annotation.NonNull;

/**
 * Created by Koo on 2017. 11. 7..
 */

public interface InfoInputSurveyDataSource {
    interface LoadInfoInputSurveyCallback {
        void onInfoInputSurveyLoaded(InfoInputSurvey infoInputSurvey);

        void onDataNotAvailable();
    }

    interface SaveInfoInputSurveyCallback {
        void onDataSaved();

        void onDataNotSaved();
    }

    void getInfoInputSurvey(@NonNull LoadInfoInputSurveyCallback callback);

    void postInfoInputSurvey(@NonNull InfoInputSurvey infoInputSurvey,
                             @NonNull SaveInfoInputSurveyCallback callback);

    int getTrainingCount();
}
