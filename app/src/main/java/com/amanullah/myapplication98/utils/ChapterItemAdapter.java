package com.amanullah.myapplication98.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.model.ChapterItem;
import com.bumptech.glide.Glide;
import java.util.List;


public class ChapterItemAdapter extends RecyclerView.Adapter<ChapterItemAdapter.MyViewHolder>{
    private Context context;
    private List<ChapterItem> itemList;
    private ChapterItemAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subTitle ,id, isfree;
        public ImageView img;
        public ConstraintLayout ct_parent;

        public MyViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.id);
            title = view.findViewById(R.id.textView7);
            subTitle = view.findViewById(R.id.textView8);
            ct_parent = view.findViewById(R.id.ct_parent);
            isfree = view.findViewById(R.id.textView12);
//            img = view.findViewById(R.id.imageView3);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemSelected(itemList.get(getAdapterPosition()));
                }
            });
        }
    }

    public ChapterItemAdapter(Context context, ChapterItemAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public ChapterItemAdapter(Context context, List<ChapterItem> itemList, ChapterItemAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChapterItem item = itemList.get(position);
        holder.id.setText(item.getId()+1+"");
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubtitle());
//        Glide.with(context).load(item.getImgUrl()).into(holder.img);

        if(item.getId()<3){
            holder.isfree.setText("Free");
            holder.isfree.setBackgroundResource(R.drawable.button_shape_bb);
            holder.ct_parent.setBackgroundColor(context.getResources().getColor(R.color.colortrans2));
        }
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
//        return 10;
    }

    public interface ChapterItemAdapterListener {
        void onItemSelected(ChapterItem item);
    }
}
