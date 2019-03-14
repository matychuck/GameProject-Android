package com.example.user.game_projekt;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    public static final int EDIT_PRODUCT_ACTIVITY_REQUEST_CODE = 0;

    class RankingViewHolder extends RecyclerView.ViewHolder {
        private final TextView userItemView_name;
        private final TextView userItemView_points;
        private final TextView userItemView_place;
        private User user;
        private RankingViewHolder(View itemView) {
            super(itemView);
           userItemView_name = itemView.findViewById(R.id.textView);
            userItemView_points = itemView.findViewById(R.id.txtTitle);
            userItemView_place = itemView.findViewById(R.id.txtNumber);

        }

        public void bindData(final User entry)
        {
           userItemView_name.setText(entry.getName());
            userItemView_points.setText(Integer.toString(entry.getPoints()));
            this.user = entry;

        }

    }

   // private ProductsViewModel pvmodel;
    private final LayoutInflater mInflater;
    private List<User> users; // Cached copy of products
    Context cont;
    RankingAdapter(Context context) { mInflater = LayoutInflater.from(context); cont = context;}

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RankingViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {

        if (users != null) {
            holder.bindData(users.get(position));
            User current = users.get(position);
            holder.userItemView_name.setText(current.getName());
            holder.userItemView_points.setText(Integer.toString(current.getPoints())+" pkt");
            holder.userItemView_place.setText(Integer.toString(position+1));
        } else {
            // Covers the case of data not being ready yet.
            holder.userItemView_name.setText("No Word");
            holder.userItemView_points.setText("No Points");
        }
    }

    public int getUserId(int position)
    {
        return users.get(position).getId();
    }

    /*void setViewModel(ProductsViewModel pvm)
    {
        pvmodel=pvm;
        notifyDataSetChanged();
    }*/

    void setUsers(List<User> usersList){
        users = usersList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        else return 0;
    }
}
