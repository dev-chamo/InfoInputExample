package com.chamodev.infoinputexample.data;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chamodev.infoinputexample.BuildConfig;
import com.chamodev.infoinputexample.util.network.ApiStores;
import com.chamodev.infoinputexample.util.network.AppClient;
import com.chamodev.infoinputexample.util.network.CustomCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Koo on 2017. 11. 7..
 */

public class InfoInputSurveyRepository implements InfoInputSurveyDataSource {
    private ApiStores mApiStores = AppClient.retrofit().create(ApiStores.class);

    private static InfoInputSurveyRepository INSTANCE = null;

    public InfoInputSurveyRepository() {
    }

    public static InfoInputSurveyRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InfoInputSurveyRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getInfoInputSurvey(@NonNull final LoadInfoInputSurveyCallback callback) {
        CustomCallback<ResponseBody> $callback = new CustomCallback<ResponseBody>() {
            @Override
            public void onSuccess(JSONObject resultJSON) {
                Gson gson = new Gson();
                InfoInputSurvey infoInputSurvey = gson.fromJson(resultJSON.toString(), InfoInputSurvey.class);
                callback.onInfoInputSurveyLoaded(infoInputSurvey);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onThrowable(Throwable t) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onFinish() {

            }
        };

        Call<ResponseBody> call = mApiStores.getInfoInputSurvey(getSessionId(), getTrainingId());

        if (!call.isExecuted()) {
            call.enqueue($callback);
        } else {
            call.clone().enqueue($callback);
        }
    }

    @Override
    public void postInfoInputSurvey(@NonNull InfoInputSurvey infoInputSurvey,
                                    @NonNull final SaveInfoInputSurveyCallback callback) {
        CustomCallback<ResponseBody> $callback = new CustomCallback<ResponseBody>() {
            @Override
            public void onSuccess(JSONObject resultJSON) {
                callback.onDataSaved();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                callback.onDataNotSaved();
            }

            @Override
            public void onThrowable(Throwable t) {
                callback.onDataNotSaved();
            }

            @Override
            public void onFinish() {
            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("sid", getSessionId());
        params.put("tid", String.valueOf(getTrainingId()));
        params.put("current_worry_choices", infoInputSurvey.getCurrentWorryQuestion().getChoicesToString());
        params.put("eating_habits_choices", infoInputSurvey.getEatingHabitsQuestion().getChoicesToString());
        params.put("special_mission_choices", infoInputSurvey.getSpecialMissionQuestion().getChoicesToString());
        params.put("habit_mission_choices", infoInputSurvey.getHabitMissionQuestion().getChoicesToString());
        params.put("about_me", infoInputSurvey.getAboutMe());

        Call<ResponseBody> call = mApiStores.postInfoInputSurvey(params);

        if (!call.isExecuted()) {
            call.enqueue($callback);
        } else {
            call.clone().enqueue($callback);
        }

    }

    @Override
    public int getTrainingCount() {
        return 1;
    }

    private String getSessionId() {
        return BuildConfig.SID;
    }

    private int getTrainingId() {
        return BuildConfig.TID;
    }
}
