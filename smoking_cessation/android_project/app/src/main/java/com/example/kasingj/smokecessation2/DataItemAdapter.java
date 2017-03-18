package com.example.kasingj.smokecessation2;

/**
 * Created by kasingj on 11/28/16.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasingj.smokecessation2.friendObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private List<FriendEntity> mItems;
    private Context mContext;
    public static final String ITEM_ID_KEY = "FRIEND_ITEM_ID";

    public DataItemAdapter(Context context, List<FriendEntity> items) {
        //super(context,R.layout.friends_list_item,items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.friends_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        final FriendEntity item = mItems.get(position);

        try {
            holder.tvName.setText( (item.getEmail() == null) ? "Unknown Friend" : item.getEmail() );
            //String imageFile = item.getImage();
            //InputStream inputStream = mContext.getAssets().open(imageFile);
            //Drawable d = Drawable.createFromPath(R.drawable.no_smoking);
            holder.imageView.setImageResource(R.drawable.no_smoking);
            //item.getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Selected " + item.getEmail(), Toast.LENGTH_SHORT ).show();
                String itemId = ""+item.getID();
                Intent intent = new Intent(mContext, FriendDetail.class);
                intent.putExtra(ITEM_ID_KEY,itemId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView7);
            mView = itemView;
        }
    }
}
