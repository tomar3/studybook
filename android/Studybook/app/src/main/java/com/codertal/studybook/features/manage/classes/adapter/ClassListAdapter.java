/*
 * Created by Talab Omar on 11/7/17 4:27 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/7/17 4:27 PM
 */

package com.codertal.studybook.features.manage.classes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codertal.studybook.R;
import com.codertal.studybook.data.classes.ClassInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder>{

    private List<ClassInfo> mClasses;
    private OnClassClickListener mOnClassClickListener;
    private ViewGroup mEmptyView;

    public interface OnClassClickListener {
        void onClassClick(ClassInfo selectedClass);
    }

    public ClassListAdapter(@NonNull OnClassClickListener onClassClickListener, @NonNull ViewGroup emptyView) {
        mOnClassClickListener = onClassClickListener;
        mEmptyView = emptyView;

        mClasses = new ArrayList<>();
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_class, parent, false);

        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        ClassInfo currentClass = mClasses.get(position);

        holder.mClassNameView.setText(currentClass.getName());
    }

    @Override
    public int getItemCount() {
        if(mClasses.isEmpty()){
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            mEmptyView.setVisibility(View.GONE);
        }

        return mClasses.size();
    }

    public void updateData(List<ClassInfo> newClasses){
        mClasses = newClasses;
        this.notifyDataSetChanged();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_class_name)
        TextView mClassNameView;


        ClassViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClassClickListener.onClassClick(mClasses.get(getAdapterPosition()));
        }
    }
}
