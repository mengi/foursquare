package com.menginar.foursquare.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.model.venuedetail.Item;
import com.menginar.foursquare.data.model.venuedetail.Item_;
import com.menginar.myflamingo.ui.adapters.BusinessAdapter;

import java.util.List;

/**
 * Kullanıcı Yorumlarının Adapter
 * */
public class UserCommentsAdapter extends BusinessAdapter<Item_, UserCommentsAdapter.UserCommentsViewHolder> {


    public UserCommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewResByItemType(int position) {
        return R.layout.user_comment_list_item;
    }

    @Override
    public UserCommentsViewHolder getViewHolder(View view) {
        return new UserCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCommentsViewHolder userCommentsViewHolder, int i) {

        Item_ item = getItem(i);
        if (item == null)
            return;

        userCommentsViewHolder.tvUserComment.setText(item.getText());
    }

    public class UserCommentsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserComment;

        public UserCommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserComment = itemView.findViewById(R.id.tv_user_comment);
        }
    }

    public void onChangeItemData(List<Item_> itemList) {
        clear();
        addAll(itemList);
        notifyDataSetChanged();
    }
}
