package com.collect.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://blog.csdn.net/zgcqflqinhao/article/details/82988316
 */
public abstract class RcvBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public Context context;
    private int layoutId;
    private List<T> dataList = new ArrayList<>();

    private OnRecItemClick<T> onRecItemClick;
    private ArrayList<Integer> idRes;

    public RcvBaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public void setOnRecItemClick(OnRecItemClick<T> onRecItemClick, Integer... idRes) {
        this.onRecItemClick = onRecItemClick;
        if (idRes.length > 0) {
            this.idRes = new ArrayList<Integer>(Arrays.asList(idRes));
        } else {
            this.idRes = new ArrayList<>();
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final T data = dataList.get(position);
        if (onRecItemClick != null) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecItemClick.onItemClick(position, data);
                }
            });
            if (!idRes.isEmpty()) {
                for (Integer viewId : idRes) {
                    try {
                        View viewById = holder.getItemView().findViewById(viewId);
                        if (viewById != null) {
                            viewById.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onRecItemClick.onItemOfViewClick(position, data, view);
                                }
                            });
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);

    public Context getContext() {
        return context;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public interface OnRecItemClick<T> {
        // Item onClick
        void onItemClick(int position, T data);

        // Item of Child View onClick
        void onItemOfViewClick(int position, T data, View view);
    }
}
