package com.chamodev.infoinputexample.survey;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.data.InfoInputSurvey;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Koo on 2016. 12. 2..
 */

public class MissionChoicesAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_SPECIAL_MISSION = 0;
    public static final int TYPE_HABIT_MISSION = 1;

    private OnMissionSelectListener mOnMissionSelectListener;
    private Context mContext;
    private List<InfoInputSurvey.QuestionChoice> mMissionArray;
    private final int mMaxRequiredCount;
    private final int mMissionType;

    public MissionChoicesAdapter(Context context, int missionType, List<InfoInputSurvey.QuestionChoice> missionList,
                                 int maxRequiredCount) {
        mContext = context;
        mMissionType = missionType;
        mMissionArray = missionList;
        mMaxRequiredCount = maxRequiredCount;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView;
        if (mMissionType == TYPE_SPECIAL_MISSION) {
            rootView = inflater.inflate(R.layout.item_special_mission_choice, null);
            return new SpecialMissionViewHolder(rootView);
        } else {
            rootView = inflater.inflate(R.layout.item_habit_mission_choice, null);
            return new HabitMissionViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SpecialMissionViewHolder) {
            SpecialMissionViewHolder viewHolder = (SpecialMissionViewHolder) holder;
            viewHolder.titleTv.setText(mMissionArray.get(position).getName());
            viewHolder.subtitleTv.setText(mMissionArray.get(position).getDescription());

            Glide.with(mContext)
                    .load(mMissionArray.get(position).getCoverImageUrl())
                    .into(viewHolder.iconIv);

            viewHolder.rootView.setTag(position);
            viewHolder.rootView.setOnClickListener(this);

            if (mMissionArray.get(position).isChecked()) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }

            viewHolder.checkBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });

        } else if (holder instanceof HabitMissionViewHolder) {
            HabitMissionViewHolder viewHolder = (HabitMissionViewHolder) holder;
            viewHolder.titleTv.setText(mMissionArray.get(position).getName());
            Glide.with(mContext)
                    .load(mMissionArray.get(position).getCoverImageUrl())
                    .into(viewHolder.iconIv);

            viewHolder.rootView.setTag(position);
            viewHolder.rootView.setOnClickListener(this);

            if (mMissionArray.get(position).isChecked()) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }

            viewHolder.checkBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMissionArray.size();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.special_mission_choice_ry:
            case R.id.habit_mission_choice_ry:
                int position = (Integer) view.getTag();
                boolean isChecked = mMissionArray.get(position).isChecked();

                if (getCheckedCount() >= mMaxRequiredCount && !isChecked) {
                    Toast.makeText(mContext, String.format("최대 %s개의 미션을 선택할 수 있어요!", mMaxRequiredCount),
                            Toast.LENGTH_LONG).show();
                    break;
                }

                view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                AppCompatCheckBox checkBox = view.findViewById(mMissionType == TYPE_SPECIAL_MISSION
                        ? R.id.special_mission_choice_cb : R.id.habit_mission_choice_cb);
                checkBox.setChecked(!isChecked);

                String coverImageUrl = mMissionArray.get(position).getCoverImageUrl();
                if (coverImageUrl != null) {
                    coverImageUrl = coverImageUrl.replace("_ios", "");
                    if (!isChecked) {
                        coverImageUrl = coverImageUrl.replace(".png", "_act.png");
                    } else {
                        coverImageUrl = coverImageUrl.replace("_act.png", ".png");
                    }
                }

                ImageView imageView = view.findViewById(mMissionType == TYPE_SPECIAL_MISSION ?
                        R.id.special_mission_choice_cover_iv : R.id.habit_mission_choice_icon_iv);
                Glide.with(mContext)
                        .load(coverImageUrl)
                        .into(imageView);

                mMissionArray.get(position).setCoverImageUrl(coverImageUrl);
                if (isChecked) {
                    mMissionArray.get(position).setUnchecked();
                } else {
                    mMissionArray.get(position).setChecked();
                }

                Animation sgAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shrink_grow);
                sgAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setLayerType(View.LAYER_TYPE_NONE, null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(sgAnimation);

                if (mOnMissionSelectListener != null) {
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < mMissionArray.size(); i++) {
                        if (mMissionArray.get(i).isChecked()) {
                            list.add(i);
                        }
                    }
                    mOnMissionSelectListener.onMissionSelect(list);
                }
                break;
        }
    }

    public int getCheckedCount() {
        int count = 0;
        for (int i = 0; i < mMissionArray.size(); i++) {
            if (mMissionArray.get(i).isChecked()) {
                count++;
            }
        }
        return count;
    }

    public void setOnMissionSelectListener(OnMissionSelectListener onMissionSelectListener) {
        mOnMissionSelectListener = onMissionSelectListener;
    }

    private static class HabitMissionViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        private TextView titleTv;
        private ImageView iconIv;
        private AppCompatCheckBox checkBox;
        private RelativeLayout checkBoxRy;

        public HabitMissionViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.habit_mission_choice_ry);
            titleTv = itemView.findViewById(R.id.habit_mission_choice_title_tv);
            iconIv = itemView.findViewById(R.id.habit_mission_choice_icon_iv);
            checkBox = itemView.findViewById(R.id.habit_mission_choice_cb);
            checkBoxRy = itemView.findViewById(R.id.habit_mission_choice_cb_ry);
        }
    }

    private static class SpecialMissionViewHolder extends RecyclerView.ViewHolder {

        private View rootView;
        private TextView titleTv;
        private TextView subtitleTv;
        private ImageView iconIv;
        private AppCompatCheckBox checkBox;
        private RelativeLayout checkBoxRy;

        public SpecialMissionViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.special_mission_choice_ry);
            titleTv = itemView.findViewById(R.id.special_mission_choice_title_tv);
            subtitleTv = itemView.findViewById(R.id.special_mission_choice_subtitle_tv);
            iconIv = itemView.findViewById(R.id.special_mission_choice_cover_iv);
            checkBox = itemView.findViewById(R.id.special_mission_choice_cb);
            checkBoxRy = itemView.findViewById(R.id.special_mission_choice_cb_ry);
        }
    }
}
