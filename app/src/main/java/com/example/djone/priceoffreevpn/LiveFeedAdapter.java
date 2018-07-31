package com.example.djone.priceoffreevpn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class LiveFeedAdapter extends RecyclerView.Adapter<LiveFeedAdapter.ViewHolder> {
    private static final String TAG = "LiveFeedAdapter";

    private ArrayList<String> mPacketText = new ArrayList<>();
//    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public LiveFeedAdapter(ArrayList<String> mPacketText, Context mContext) {
        this.mPacketText = mPacketText;
//        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livefeed_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }//viewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called");//will log when an item has been logged to live feed

        holder.packetText.setText(mPacketText.get(position));
        holder.liveFeedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }//will be used for the onclick of the separate packets in the recycler view
        });
    }

    @Override
    public int getItemCount() {

        return mPacketText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView image;
        TextView packetText;
        ConstraintLayout liveFeedLayout;

        public ViewHolder(View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.image);
            packetText = itemView.findViewById(R.id.liveFeedContent);
            liveFeedLayout = itemView.findViewById(R.id.liveFeedLayout);
        }
    }
}
