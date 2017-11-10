package com.chamodev.infoinputexample.welcome;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.data.InfoInputSurveyRepository;

public class InfoInputWelcomeActivity extends AppCompatActivity {

    public static final int REQUEST_INFO_INPUT_SURVEY = 1;

    private InfoInputWelcomePresenter mInfoInputWelcomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_input_welcome);

        FragmentManager fragmentManager = getSupportFragmentManager();

        InfoInputWelcomeFragment fragment = (InfoInputWelcomeFragment)
                fragmentManager.findFragmentById(R.id.info_input_welcome_container_fl);

        if (fragment == null) {
            fragment = InfoInputWelcomeFragment.newInstance();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.info_input_welcome_container_fl, fragment);
            transaction.commit();
        }

        InfoInputSurveyRepository repository = InfoInputSurveyRepository.getInstance();

        mInfoInputWelcomePresenter = new InfoInputWelcomePresenter(repository, fragment);
    }
}
