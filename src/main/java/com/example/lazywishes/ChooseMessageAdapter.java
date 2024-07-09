package com.example.lazywishes;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static com.example.lazywishes.MessageDbHelper.getMessage;
import static com.google.android.material.internal.ContextUtils.getActivity;


import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ChooseMessageAdapter extends RecyclerView.Adapter<ChooseMessageAdapter.ViewHolder>{
    private List<String> mMessages;

    private Context mContext;
    private int mSelectedPosition = RecyclerView.NO_POSITION;
    public ChooseMessageAdapter(List<String> messages,Context context) {
        mMessages = messages;
        mContext = context;
    }
    @SuppressLint("Range")
    @NonNull
    @Override

    public ChooseMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_choose_message_item, parent, false);
        ChooseMessageAdapter.ViewHolder viewHolder = new ViewHolder(view);


        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag =v.getTag();
                String selectedMessage = ((TextView) v).getText().toString();
                setSelectedMessage(selectedMessage);
                if (tag != null) {
                    int position = (int) tag;
                    if (position != mSelectedPosition)
                        setSelectedPosition(position);
                }
            }
        });
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ChooseMessageAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mMessages.get(position));
        if (mSelectedPosition == position) {
            holder.textView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.textView.setBackgroundColor(Color.TRANSPARENT);
        }




    }
    public int setSelectedPosition(int position) {
        int oldPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(mSelectedPosition);
        return position;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();

    }
    public void setSelectedMessage(String message) {
        MessageData.getInstance().setMessage(message);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
