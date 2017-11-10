package com.chamodev.infoinputexample.survey;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chamodev.infoinputexample.BaseFragment;
import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.data.InfoInputSurvey;
import com.chamodev.infoinputexample.data.InfoInputSurveyType;
import com.chamodev.infoinputexample.util.CommonUtils;
import com.chamodev.infoinputexample.util.SpaceItemDecoration;
import com.chamodev.infoinputexample.util.widget.FlowTagAdapter;
import com.chamodev.infoinputexample.util.widget.FlowTagLayout;
import com.chamodev.infoinputexample.util.widget.OnTagSelectListener;

import java.util.ArrayList;
import java.util.List;


public class InfoInputSurveyFragment extends BaseFragment implements InfoInputSurveyContract.View,
        View.OnClickListener {

    private InfoInputSurveyContract.Presenter mPresenter;

    private TextView mCurrentPageTv;
    private TextView mTitleTv;
    private TextView mSubTitleTv;
    private FloatingActionButton mNextBtn;

    private View mBasicSurveyView;
    private View mSpecialMissionSurveyView;
    private View mHabitMissionSurveyView;
    private View mMealTimeAlarmSurveyView;
    private View mWorkoutTimeAlarmSurveyView;
    private View mAboutMeSurveyView;

    public static InfoInputSurveyFragment newInstance() {
        return new InfoInputSurveyFragment();
    }

    public InfoInputSurveyFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info_input_survey, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.info_input_header_toolbar);
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mCurrentPageTv = rootView.findViewById(R.id.info_input_header_page_tv);

        mTitleTv = rootView.findViewById(R.id.info_input_header_title_tv);

        mSubTitleTv = rootView.findViewById(R.id.info_input_header_subtitle_tv);

        mNextBtn = rootView.findViewById(R.id.info_input_next_btn);
        mNextBtn.setOnClickListener(this);

        mBasicSurveyView = rootView.findViewById(R.id.info_input_basic_survey);

        mSpecialMissionSurveyView = rootView.findViewById(R.id.info_input_special_mission_survey);
        mHabitMissionSurveyView = rootView.findViewById(R.id.info_input_habit_mission_survey);
        mMealTimeAlarmSurveyView = rootView.findViewById(R.id.info_input_meal_time_alarm_survey);
        mWorkoutTimeAlarmSurveyView = rootView.findViewById(R.id.info_input_workout_time_alarm_survey);
        mAboutMeSurveyView = rootView.findViewById(R.id.info_input_about_me_survey);


        return rootView;
    }

    @Override
    public void setPresenter(InfoInputSurveyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyKeywordSurvey(final InfoInputSurvey.SurveyQuestion currentWorryQuestion,
                                    final InfoInputSurvey.SurveyQuestion eatingHabitsQuestion) {
        mBasicSurveyView.setVisibility(View.VISIBLE);
        setNextButtonEnabled(currentWorryQuestion.hasValidAnswer()
                && eatingHabitsQuestion.hasValidAnswer());

        TextView currentWorriesTitleTv = mBasicSurveyView.findViewById(R.id.info_input_current_worry_title_tv);
        currentWorriesTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.biggest_my_worries));

        FlowTagLayout currentWorryFlowTagLayout = mBasicSurveyView.findViewById(R.id.info_input_current_worry_ftl);
        FlowTagLayout eatingHabitsFlowTagLayout = mBasicSurveyView.findViewById(R.id.info_input_eating_habits_ftl);

        currentWorryFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        eatingHabitsFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);

        List<String> dataSource = new ArrayList<>();
        List<Integer> selectedDataSource = new ArrayList<>();

        for (int i = 0; i < currentWorryQuestion.getChoices().size(); i++) {
            InfoInputSurvey.QuestionChoice choice = currentWorryQuestion.getChoices().get(i);
            dataSource.add(choice.getName());
            if (choice.isChecked()) {
                selectedDataSource.add(i);
            }
        }

        FlowTagAdapter currentWorryFlowTagAdapter = new FlowTagAdapter<>(mActivity, selectedDataSource);
        currentWorryFlowTagLayout.setAdapter(currentWorryFlowTagAdapter);
        currentWorryFlowTagAdapter.onlyAddAll(dataSource);
        currentWorryFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                currentWorryQuestion.resetChoicesCheck();
                for (int position : selectedList) {
                    currentWorryQuestion.getChoices().get(position).setChecked();
                }

                setNextButtonEnabled(currentWorryQuestion.hasValidAnswer()
                        && eatingHabitsQuestion.hasValidAnswer());
            }
        });

        List<String> dataSource2 = new ArrayList<>();
        List<Integer> selectedDataSource2 = new ArrayList<>();

        for (int i = 0; i < eatingHabitsQuestion.getChoices().size(); i++) {
            InfoInputSurvey.QuestionChoice choice = eatingHabitsQuestion.getChoices().get(i);
            dataSource2.add(choice.getName());
            if (choice.isChecked()) {
                selectedDataSource2.add(i);
            }
        }

        FlowTagAdapter eatingHabitsFlowTagAdapter = new FlowTagAdapter<>(mActivity, selectedDataSource2);
        eatingHabitsFlowTagLayout.setAdapter(eatingHabitsFlowTagAdapter);
        eatingHabitsFlowTagAdapter.onlyAddAll(dataSource2);
        eatingHabitsFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                eatingHabitsQuestion.resetChoicesCheck();
                for (int position : selectedList) {
                    eatingHabitsQuestion.getChoices().get(position).setChecked();
                }

                setNextButtonEnabled(currentWorryQuestion.hasValidAnswer()
                        && eatingHabitsQuestion.hasValidAnswer());
            }
        });
    }

    @Override
    public void showSpecialMissionSurvey(final InfoInputSurvey.SurveyQuestion specialMissionQuestion) {
        mSpecialMissionSurveyView.setVisibility(View.VISIBLE);
        setNextButtonEnabled(specialMissionQuestion.hasValidAnswer());

        MissionChoicesAdapter missionChoicesAdapter = new MissionChoicesAdapter(mActivity, MissionChoicesAdapter.TYPE_SPECIAL_MISSION,
                specialMissionQuestion.getChoices(), specialMissionQuestion.getMaxRequireCount());
        RecyclerView specialMissionRv = mSpecialMissionSurveyView.findViewById(R.id.info_input_mission_set_rv);
        specialMissionRv.setLayoutManager(new LinearLayoutManager(mActivity));
        specialMissionRv.addItemDecoration(new SpaceItemDecoration(CommonUtils.convertToPixelFromDP(mActivity, 5), false));
        specialMissionRv.setAdapter(missionChoicesAdapter);

        missionChoicesAdapter.setOnMissionSelectListener(new OnMissionSelectListener() {
            @Override
            public void onMissionSelect(List<Integer> selectedList) {
                specialMissionQuestion.resetChoicesCheck();
                for (int position : selectedList) {
                    specialMissionQuestion.getChoices().get(position).setChecked();
                }

                setNextButtonEnabled(specialMissionQuestion.hasValidAnswer());
            }
        });
    }

    @Override
    public void showHabitMissionSurvey(final InfoInputSurvey.SurveyQuestion habitMissionQuestion) {
        mHabitMissionSurveyView.setVisibility(View.VISIBLE);
        setNextButtonEnabled(habitMissionQuestion.hasValidAnswer());

        MissionChoicesAdapter missionChoicesAdapter = new MissionChoicesAdapter(mActivity, MissionChoicesAdapter.TYPE_HABIT_MISSION,
                habitMissionQuestion.getChoices(), habitMissionQuestion.getMaxRequireCount());
        RecyclerView habitMissionRv = mHabitMissionSurveyView.findViewById(R.id.info_input_mission_set_rv);
        habitMissionRv.setLayoutManager(new GridLayoutManager(mActivity, 2));
        habitMissionRv.addItemDecoration(new SpaceItemDecoration(CommonUtils.convertToPixelFromDP(mActivity, 5), true));
        habitMissionRv.setAdapter(missionChoicesAdapter);

        missionChoicesAdapter.setOnMissionSelectListener(new OnMissionSelectListener() {
            @Override
            public void onMissionSelect(List<Integer> selectedList) {
                habitMissionQuestion.resetChoicesCheck();
                for (int position : selectedList) {
                    habitMissionQuestion.getChoices().get(position).setChecked();
                }

                setNextButtonEnabled(habitMissionQuestion.hasValidAnswer());
            }
        });
    }

    @Override
    public void showMealTimeAlarmSurvey(boolean hasMealTimeAlarm, final String[] alarmTimeArray) {
        mMealTimeAlarmSurveyView.setVisibility(View.VISIBLE);
        setNextButtonEnabled(hasMealTimeAlarm);

        ListView mTimeSettingLv = mMealTimeAlarmSurveyView.findViewById(R.id.info_input_alarm_settings_lv);

        final AlarmSettingAdapter alarmSettingAdapter = new AlarmSettingAdapter(mActivity, 0,
                hasMealTimeAlarm, alarmTimeArray);
        mTimeSettingLv.setAdapter(alarmSettingAdapter);
        mTimeSettingLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                int hour = Integer.parseInt(alarmTimeArray[i].substring(0, 2));
                int minute = Integer.parseInt(alarmTimeArray[i].substring(2, 4));

                showTimePicker(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        String time = String.format("%s%d%s%d", h < 10 ? "0" : "", h, m < 10 ? "0" : "", m);
                        String[] timeArray = alarmSettingAdapter.setAlarmTime(i, time);
                        if (timeArray != null) {
                            setNextButtonEnabled(true);
                            mPresenter.setMealTimeAlarm(timeArray);
                        }
                    }
                }, hour, minute);
            }
        });
    }

    @Override
    public void showWorkoutTimeAlarmSurvey(boolean hasWorkoutTimeAlarm, final String[] alarmTimeArray) {
        mWorkoutTimeAlarmSurveyView.setVisibility(View.VISIBLE);
        setNextButtonEnabled(hasWorkoutTimeAlarm);

        ListView mTimeSettingLv = mWorkoutTimeAlarmSurveyView.findViewById(R.id.info_input_alarm_settings_lv);

        final AlarmSettingAdapter alarmSettingAdapter = new AlarmSettingAdapter(mActivity, 1,
                hasWorkoutTimeAlarm, alarmTimeArray);
        mTimeSettingLv.setAdapter(alarmSettingAdapter);
        mTimeSettingLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                int hour = Integer.parseInt(alarmTimeArray[i].substring(0, 2));
                int minute = Integer.parseInt(alarmTimeArray[i].substring(2, 4));

                showTimePicker(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        String time = String.format("%s%d%s%d", h < 10 ? "0" : "", h, m < 10 ? "0" : "", m);
                        String[] timeArray = alarmSettingAdapter.setAlarmTime(i, time);
                        if (timeArray != null) {
                            setNextButtonEnabled(true);
                            mPresenter.setWorkoutTimeAlarm(timeArray);
                        }
                    }
                }, hour, minute);
            }
        });
    }

    @Override
    public void showAboutMeSurvey(String aboutMe) {
        mAboutMeSurveyView.setVisibility(View.VISIBLE);

        EditText aboutMeEt = mAboutMeSurveyView.findViewById(R.id.about_me_et);
        aboutMeEt.setText(aboutMe);

        aboutMeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.setAboutMe(editable.toString());
            }
        });
    }

    @Override
    public void hideSurvey(InfoInputSurveyType surveyType) {
        switch (surveyType) {
            case BASIC_INFO:
                mBasicSurveyView.setVisibility(View.GONE);
                break;

            case SPECIAL_MISSION:
                mSpecialMissionSurveyView.setVisibility(View.GONE);
                break;

            case HABIT_MISSION:
                mHabitMissionSurveyView.setVisibility(View.GONE);
                break;

            case MEAL_TIME_ALARM:
                mMealTimeAlarmSurveyView.setVisibility(View.GONE);
                break;

            case WORKOUT_TIME_ALARM:
                mWorkoutTimeAlarmSurveyView.setVisibility(View.GONE);
                break;

            case ABOUT_ME:
                mAboutMeSurveyView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showInputInfoWelcomePage(boolean isComplete) {
        if (isComplete) {
            mActivity.setResult(Activity.RESULT_OK);
        }
        mActivity.finish();
    }

    @Override
    public void setSurveyHeader(InfoInputSurveyType surveyType) {
        switch (surveyType) {
            case BASIC_INFO:
                mTitleTv.setText(R.string.basic_info_input);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.choose_keywords_about_you));
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;

            case SPECIAL_MISSION:
                mTitleTv.setText(R.string.class_special_mission);
                mSubTitleTv.setText(R.string.it_is_challenge_missions);
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;

            case HABIT_MISSION:
                mTitleTv.setText(R.string.habit_molding_mission);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.choose_habits_you_want));
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;

            case MEAL_TIME_ALARM:
                mTitleTv.setText(R.string.meal_time_alarm_setting);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.when_do_you_eat));
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;

            case WORKOUT_TIME_ALARM:
                mTitleTv.setText(R.string.workout_time_alarm_setting);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.when_do_you_workout));
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;

            case ABOUT_ME:
                mTitleTv.setText(R.string.something_to_say);
                mSubTitleTv.setText(CommonUtils.substituteUserName(mActivity, R.string.have_you_something_to_say));
                mCurrentPageTv.setText(String.valueOf(surveyType.ordinal()));
                break;
        }

    }

    @Override
    public void setNextButtonEnabled(boolean enabled) {
        mNextBtn.setEnabled(enabled);
        if (enabled) {
            mNextBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary)));
        } else {
            mNextBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
        }
    }

    @Override
    public void showTimePicker(TimePickerDialog.OnTimeSetListener onTimeSetListener,
                               int hour, int minute) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity, onTimeSetListener,
                hour, minute, false);
        timePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.goToPreviousStep();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_input_next_btn:
                mPresenter.goToNextStep();
                break;
        }
    }
}
