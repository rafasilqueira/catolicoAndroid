package rz.com.catolico.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> mItems;

    public abstract RecyclerView.ViewHolder setupViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindData(RecyclerView.ViewHolder holder, final T item);

    public GenericAdapter(Context context , List<T> mItems) {
        this.context = context;
        this.mItems = mItems != null ? mItems : new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = setupViewHolder(parent, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, this.mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public T getItem(int position) {
        return this.mItems.get(position);
    }

    public Context getContext() {
        return this.context;
    }

}