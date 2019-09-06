package com.collect.library.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * https://blog.csdn.net/zgcqflqinhao/article/details/82988316
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View itemView;
    private SparseArray<View> viewSparseArray;

    BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.viewSparseArray = new SparseArray<View>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
