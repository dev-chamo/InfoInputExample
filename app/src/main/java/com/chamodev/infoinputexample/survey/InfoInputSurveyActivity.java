package com.chamodev.infoinputexample.survey;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.data.InfoInputSurveyRepository;
import com.chamodev.infoinputexample.welcome.InfoInputWelcomeFragment;
import com.chamodev.infoinputexample.welcome.InfoInputWelcomePresenter;

public class InfoInputSurveyActivity extends AppCompatActivity {

    private InfoInputSurveyPresenter mInfoInputSurveyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_input_survey);

        FragmentManager fragmentManager = getSupportFragmentManager();

        InfoInputSurveyFragment fragment = (InfoInputSurveyFragment)
                fragmentManager.findFragmentById(R.id.info_input_container_fl);

        if (fragment == null) {
            fragment = InfoInputSurveyFragment.newInstance();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.info_input_container_fl, fragment);
            transaction.commit();
        }

        InfoInputSurveyRepository repository = InfoInputSurveyRepository.getInstance();

        mInfoInputSurveyPresenter = new InfoInputSurveyPresenter(repository, fragment);
    }
}
