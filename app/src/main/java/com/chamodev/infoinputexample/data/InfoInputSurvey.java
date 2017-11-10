package com.chamodev.infoinputexample.data;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Koo on 2017. 11. 6..
 */

public class InfoInputSurvey {
    @SerializedName("current_worry")
    private SurveyQuestion mCurrentWorryQuestion;

    @SerializedName("eating_habits")
    private SurveyQuestion mEatingHabitsQuestion;

    @SerializedName("special_mission")
    private SurveyQuestion mSpecialMissionQuestion;

    @SerializedName("habit_mission")
    private SurveyQuestion mHabitMissionQuestion;

    @SerializedName("about_me")
    private String mAboutMe;

    @SerializedName("meal_time")
    private String[] mMealTimeArray;

    @SerializedName("workout_time")
    private String[] mWorkoutTimeArray;

    public InfoInputSurvey(SurveyQuestion currentWorryQuestion, SurveyQuestion eatingHabitsQuestion,
                           SurveyQuestion specialMissionQuestion, SurveyQuestion habitMissionQuestion,
                           String aboutMe, String[] mealTimeArray, String[] workoutTimeArray) {
        mCurrentWorryQuestion = currentWorryQuestion;
        mEatingHabitsQuestion = eatingHabitsQuestion;
        mSpecialMissionQuestion = specialMissionQuestion;
        mHabitMissionQuestion = habitMissionQuestion;
        mAboutMe = aboutMe;
        mMealTimeArray = mealTimeArray;
        mWorkoutTimeArray = workoutTimeArray;
    }

    public SurveyQuestion getCurrentWorryQuestion() {
        return mCurrentWorryQuestion;
    }

    public SurveyQuestion getEatingHabitsQuestion() {
        return mEatingHabitsQuestion;
    }

    public SurveyQuestion getSpecialMissionQuestion() {
        return mSpecialMissionQuestion;
    }

    public SurveyQuestion getHabitMissionQuestion() {
        return mHabitMissionQuestion;
    }

    public String getAboutMe() {
        return mAboutMe;
    }

    public String[] getMealTimeArray() {
        return mMealTimeArray;
    }

    public String[] getWorkoutTimeArray() {
        return mWorkoutTimeArray;
    }

    public void setCurrentWorryQuestion(SurveyQuestion currentWorryQuestion) {
        mCurrentWorryQuestion = currentWorryQuestion;
    }

    public void setEatingHabitsQuestion(SurveyQuestion eatingHabitsQuestion) {
        mEatingHabitsQuestion = eatingHabitsQuestion;
    }

    public void setSpecialMissionQuestion(SurveyQuestion specialMissionQuestion) {
        mSpecialMissionQuestion = specialMissionQuestion;
    }

    public void setHabitMissionQuestion(SurveyQuestion habitMissionQuestion) {
        mHabitMissionQuestion = habitMissionQuestion;
    }

    public void setAboutMe(String aboutMe) {
        mAboutMe = aboutMe;
    }

    public void setMealTimeArray(String[] mealTimeArray) {
        mMealTimeArray = mealTimeArray;
    }

    public void setWorkoutTimeArray(String[] workoutTimeArray) {
        mWorkoutTimeArray = workoutTimeArray;
    }

    public class SurveyQuestion {
        @SerializedName("max_require_count")
        private final int mMaxRequireCount;

        @SerializedName("min_require_count")
        private final int mMinRequireCount;

        @SerializedName("choices")
        private List<QuestionChoice> mChoices;

        @SerializedName("question")
        private String mTitle;

        public SurveyQuestion(int maxRequireCount, int minRequireCount, List<QuestionChoice> choices) {
            mMaxRequireCount = maxRequireCount;
            mMinRequireCount = minRequireCount;
            mChoices = choices;
        }

        public int getMaxRequireCount() {
            return mMaxRequireCount;
        }

        public int getMinRequireCount() {
            return mMinRequireCount;
        }

        public List<QuestionChoice> getChoices() {
            return mChoices;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public void resetChoicesCheck() {
            for (QuestionChoice choice : mChoices) {
                choice.setUnchecked();
            }
        }

        public int getChoicesCheckedCount() {
            int count = 0;
            for (QuestionChoice choice : mChoices) {
                if (choice.isChecked()) {
                    count++;
                }
            }
            return count;
        }

        public String getChoicesToString() {
            List<Integer> resultList = new ArrayList<>();
            for (int i = 0; i < mChoices.size(); i++) {
                QuestionChoice choice = mChoices.get(i);
                if (choice.isChecked()) {
                    resultList.add(mChoices.get(i).getId());
                }
            }
            return String.format("[%s]", TextUtils.join(",", resultList));
        }

        public boolean hasValidAnswer() {
            return getChoicesCheckedCount() >= mMinRequireCount
                    && getChoicesCheckedCount() <= mMaxRequireCount;
        }
    }

    public class QuestionChoice {
        @SerializedName("id")
        private final int mId;

        @SerializedName("name")
        private final String mName;

        @SerializedName("checked")
        private int mIsChecked;

        @SerializedName("cover_image_url")
        private String mCoverImageUrl;

        @SerializedName("description")
        private String mDescription;

        public QuestionChoice(int id, String name, boolean isChecked) {
            mId = id;
            mName = name;
            mIsChecked = isChecked ? 1 : 0;
        }

        public int getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public boolean isChecked() {
            return mIsChecked == 1;
        }

        public void setChecked() {
            mIsChecked = 1;
        }

        public void setUnchecked() {
            mIsChecked = 0;
        }

        public String getCoverImageUrl() {
            return mCoverImageUrl;
        }

        public void setCoverImageUrl(String coverImageUrl) {
            mCoverImageUrl = coverImageUrl;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }
    }

}
