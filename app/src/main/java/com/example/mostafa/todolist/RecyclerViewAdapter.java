package com.example.mostafa.todolist;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Models.TODOitem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<TODOitem> mItems= new ArrayList<TODOitem>();
    private Context mContext;
    private MainPresenter presenter;

    public RecyclerViewAdapter(MainPresenter presenter, ArrayList<TODOitem> mItems, Context context){
        this.presenter=presenter;
        this.mItems=mItems;
        this.mContext=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    //TODO: stopped here (video called RecyclerView by CodingWithMitch at minute 13:45)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: item being added");
        if(!mItems.isEmpty() && holder != null && holder.itemName!= null) {
            Log.i(TAG, "onBindViewHolder: added");
            holder.itemName.setText(mItems.get(position).title);
            holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    presenter.onItemClicked(position);
                    notifyDataSetChanged();
                    return false;
                }
            });
        }
        else{
            Log.i(TAG, "onBindViewHolder: failed");
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.parent_layout)
        RelativeLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
