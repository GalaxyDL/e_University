package com.wangh.e_university;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by Galaxy on 2017/11/6.
 */

public class LostPropertyAdapter extends RecyclerView.Adapter<LostPropertyAdapter.LostPropertyHolder> {
    private static final String TAG = "LostPropertyAdapter";
    private ArrayList<LostProperty> lostProperties = new ArrayList<>();
    private ArrayList<Boolean> hasPhoto = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private OnclickListener listener;

    private HashMap<String, Bitmap> photoCache = new HashMap<>();

    private ArrayList<Boolean> downloaded = new ArrayList<>();

    public interface OnclickListener {
        void onClick(View view, int position, LostPropertyHolder holder);
    }

    @Override
    public LostPropertyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LostPropertyHolder holder = new LostPropertyHolder(inflater.inflate(R.layout.lost_property_item, parent, false), listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LostPropertyHolder holder, final int position) {
        final LostProperty lostProperty = lostProperties.get(position);
        Log.d(TAG, "onBindViewHolder: " + lostProperty);
        holder.mTitleTextView.setText(lostProperty.getTitle());
        holder.mPhoneTextView.setText(lostProperty.getPhone());
        holder.mDescriptionTextView.setText(lostProperty.getDescription());
        if (lostProperty.getFound()) {
            holder.mLostOrFoundTextView.setText(R.string.lost_property_found);
        } else {
            holder.mLostOrFoundTextView.setText(R.string.lost_property_lost);
        }
        if (lostProperty.getUser().equals("20152739")) {
            holder.mDeleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.mDeleteButton.setVisibility(View.GONE);
        }
//        holder.mPhotoImageView.setVisibility(View.GONE);
        if (hasPhoto.get(position)) {
            holder.mPhotoImageView.setVisibility(View.VISIBLE);
            if (photoCache.get(lostProperty.getObjectId()) != null) {
                holder.mPhotoImageView.setImageBitmap(photoCache.get(lostProperty.getObjectId()));
            } else {
                lostProperty.getPhoto().download(new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(s);
                            holder.mPhotoImageView.setImageBitmap(bitmap);
                            downloaded.set(position, true);
                            photoCache.put(lostProperty.getObjectId(), bitmap);
                            Log.d(TAG, "done: " + s);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            }
        } else {
            holder.mPhotoImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lostProperties.size();
    }

    public void addLostProperty(LostProperty lostProperty) {
        lostProperties.add(lostProperty);
        downloaded.add(false);
        hasPhoto.add(lostProperty.getPhoto() != null);
    }

    public LostProperty getLostProperty(int position) {
        return lostProperties.get(position);
    }

    public void removeLostProperty(int position) {
        lostProperties.remove(position);
        downloaded.remove(position);
        hasPhoto.remove(position);
    }

    public void removeAll() {
        while (!lostProperties.isEmpty()) {
            lostProperties.remove(0);
            downloaded.remove(0);
            hasPhoto.remove(0);
        }
    }

    public void setListener(OnclickListener listener) {
        this.listener = listener;
    }

    public LostPropertyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    static class LostPropertyHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoImageView;
        TextView mTitleTextView;
        TextView mPhoneTextView;
        TextView mDescriptionTextView;
        TextView mLostOrFoundTextView;
        Button mDeleteButton;
        CardView mCardView;
        LostPropertyHolder holder = this;

        LostPropertyHolder(final View itemView, final OnclickListener listener) {
            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.lostPropertyPhoto);
            mTitleTextView = (TextView) itemView.findViewById(R.id.lostPropertyTitle);
            mPhoneTextView = (TextView) itemView.findViewById(R.id.lostPropertyPhone);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.lostPropertyDescription);
            mDeleteButton = (Button) itemView.findViewById(R.id.lostPropertyDelete);
            mLostOrFoundTextView = (TextView) itemView.findViewById(R.id.lostPropertyLostOrFound);
            mCardView = (CardView) itemView.findViewById(R.id.lostPropertyCard);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view, getAdapterPosition(), holder);
                }
            });
        }
    }
}
