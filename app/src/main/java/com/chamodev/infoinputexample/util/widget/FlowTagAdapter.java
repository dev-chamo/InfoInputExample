package com.chamodev.infoinputexample.util.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.chamodev.infoinputexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koo on 2017. 11. 7..
 */

public class FlowTagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;
    private final List<Integer> mSelectedDataList;

    public FlowTagAdapter(Context context, List<Integer> selectedDataList) {
        mContext = context;
        mDataList = new ArrayList<>();
        mSelectedDataList = selectedDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_flow_tag, null);

        TextView textView = view.findViewById(R.id.flow_tag_tv);
        T t = mDataList.get(position);

        if (t instanceof String) {
            textView.setText((String) t);
        }

        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        for (int selectedIndex : mSelectedDataList){
            if (selectedIndex == position){
                return true;
            }
        }
        return false;
    }
}