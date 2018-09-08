package com.example.djone.priceoffreevpn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mPacketText = new ArrayList<>();
    private Context mContext;

    RecyclerViewAdapter(ArrayList<String> mPacketText, Context mContext) {
        this.mPacketText = mPacketText;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }//viewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called");//will log when an item has been logged to live feed

        holder.packetText.setText(mPacketText.get(position));
        holder.recyclerViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }//will be used for the onclick of the separate packets in the recycler view
        });
    }

    @Override
    public int getItemCount() {

        return mPacketText.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView image;
        TextView packetText;
        ConstraintLayout recyclerViewLayout;

        ViewHolder(View itemView) {
            super(itemView);
            packetText = itemView.findViewById(R.id.recyclerViewContent);
            recyclerViewLayout = itemView.findViewById(R.id.recyclerViewLayout);
        }
    }
}
