package com.chamodev.infoinputexample.survey;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chamodev.infoinputexample.R;
import com.chamodev.infoinputexample.util.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Koo on 15. 12. 11..
 */
public class AlarmSettingAdapter extends BaseAdapter {
    private static final int[] TIMEZONE_NAMES = {R.string.breakfast, R.string.lunch, R.string.dinner};
    private static final int[] DAYS_OF_WEEK_NAMES = {R.string.mon, R.string.tue, R.string.wed, R.string.thu, R.string.fri, R.string.sat, R.string.sun};

    private static final int[] TIMEZONE_ICONS = {R.drawable.meal_morning_icon_nor, R.drawable.meal_lunch_icon_nor, R.drawable.meal_dinner_icon_nor};
    private static final int[] DAYS_OF_WEEK_ICONS = {R.drawable.ex_monday_icon_nor, R.drawable.ex_tuesday_icon_nor, R.drawable.ex_wednesday_icon_nor,
            R.drawable.ex_tuesday_icon_nor, R.drawable.ex_friday_icon_nor, R.drawable.ex_saturday_icon_nor, R.drawable.ex_sunday_icon_nor};
    private static final int[] SELECTED_TIMEZONE_ICONS = {R.drawable.meal_morning_icon_sel, R.drawable.meal_lunch_icon_sel, R.drawable.meal_dinner_icon_sel};
    private static final int[] SELECTED_DAYS_OF_WEEK_ICONS = {R.drawable.ex_monday_icon_sel, R.drawable.ex_tuesday_icon_sel, R.drawable.ex_wednesday_icon_sel,
            R.drawable.ex_tuesday_icon_sel, R.drawable.ex_friday_icon_sel, R.drawable.ex_saturday_icon_sel, R.drawable.ex_sunday_icon_sel};

    private Context mContext;

    private int mContentType;
    private boolean[] mIsCheckedArray;
    private String[] mTimeArray;
    private SparseArray<ArrayList<View>> mViewArray = new SparseArray<>();

    private ViewHolder mViewHolder;

    private interface ContentType {
        int TIMEZONE = 0;
        int WEEKS = 1;
    }

    public AlarmSettingAdapter(Context context, int type, boolean isAllChecked, String[] timeArray) {
        mContext = context;
        mContentType = type;
        if (type == ContentType.TIMEZONE) {
            mIsCheckedArray = new boolean[]{
                    isAllChecked,
                    isAllChecked,
                    isAllChecked
            };
        } else {
            mIsCheckedArray = new boolean[]{
                    isAllChecked,
                    isAllChecked,
                    isAllChecked,
                    isAllChecked,
                    isAllChecked,
                    isAllChecked,
                    isAllChecked
            };

        }
        mTimeArray = timeArray;
    }

    @Override
    public int getCount() {
        int count = 0;
        switch (mContentType) {
            case ContentType.TIMEZONE:
                count = TIMEZONE_NAMES.length;
                break;
            case ContentType.WEEKS:
                count = DAYS_OF_WEEK_NAMES.length;
                break;
        }
        return count + 1;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ArrayList<View> getItem(int position) {
        return mViewArray.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (position == getCount() - 1) {
            return layoutInflater.inflate(R.layout.dummy, null);
        }

        mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_alarm_settings, null);

            mViewHolder.iconIv = convertView.findViewById(R.id.alarm_settings_icon_iv);
            mViewHolder.titleTv = convertView.findViewById(R.id.alarm_settings_title_tv);
            mViewHolder.timeTv = convertView.findViewById(R.id.alarm_settings_time_tv);
            ArrayList<View> tmpList = new ArrayList<>();
            tmpList.add(mViewHolder.iconIv);
            tmpList.add(mViewHolder.titleTv);
            tmpList.add(mViewHolder.timeTv);
            mViewArray.put(position, tmpList);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        switch (mContentType) {
            case ContentType.WEEKS:
                mViewHolder.titleTv.setText(DAYS_OF_WEEK_NAMES[position]);
                mViewHolder.iconIv.setImageResource(DAYS_OF_WEEK_ICONS[position]);
                if (mIsCheckedArray[position]) {
                    mViewHolder.titleTv.setTextColor(mContext.getResources().getColor(R.color.black));
                    mViewHolder.timeTv.setTextColor(mContext.getResources().getColor(R.color.greyish));
                    mViewHolder.timeTv.setText(CommonUtils.getFormattedTime(mTimeArray[position]));
                    mViewHolder.iconIv.setImageResource(SELECTED_DAYS_OF_WEEK_ICONS[position]);
                }
                break;

            case ContentType.TIMEZONE:
                mViewHolder.titleTv.setText(TIMEZONE_NAMES[position]);
                mViewHolder.iconIv.setImageResource(TIMEZONE_ICONS[position]);
                if (mIsCheckedArray[position]) {
                    mViewHolder.titleTv.setTextColor(mContext.getResources().getColor(R.color.black));
                    mViewHolder.timeTv.setTextColor(mContext.getResources().getColor(R.color.greyish));
                    mViewHolder.timeTv.setText(CommonUtils.getFormattedTime(mTimeArray[position]));
                    mViewHolder.iconIv.setImageResource(SELECTED_TIMEZONE_ICONS[position]);
                }
                break;
        }

        return convertView;
    }

    private boolean isAllChecked() {
        if (mContentType == ContentType.WEEKS) {
            return mIsCheckedArray[0] && mIsCheckedArray[1]
                    && mIsCheckedArray[2] && mIsCheckedArray[3] && mIsCheckedArray[4]
                    && mIsCheckedArray[5] && mIsCheckedArray[6];
        }
        return mIsCheckedArray[0] && mIsCheckedArray[1] && mIsCheckedArray[2];
    }

    public String[] setAlarmTime(int index, String time) {
        if (!isAllChecked()) {
            if (mContentType == ContentType.WEEKS) {
                for (int i = 0; i < 7; i++) {
                    mTimeArray[i] = time;
                    mIsCheckedArray[i] = true;
                }
            }
        }

        mTimeArray[index] = time;
        mIsCheckedArray[index] = true;

        notifyDataSetChanged();

        if (isAllChecked()){
            return mTimeArray;
        }

        return null;
    }

    static class ViewHolder {
        ImageView iconIv;
        TextView titleTv;
        TextView timeTv;
    }

}
