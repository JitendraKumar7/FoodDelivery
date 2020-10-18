package com.rch.etawah.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rch.etawah.ConstantUtil.Constant;
import com.rch.etawah.CustomUtil.GlideApp;
import com.rch.etawah.ManagementUtil.Management;
import com.rch.etawah.ObjectUtil.DataObject;
import com.rch.etawah.ObjectUtil.EmptyObject;
import com.rch.etawah.ObjectUtil.ProgressObject;
import com.rch.etawah.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public class ChattingAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int PROGRESS_VIEW = 2;
    private int FROM_CHAT_VIEW = 3;
    private int TO_CHAT_VIEW = 4;
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private Management management;


    public ChattingAdapter(Context context, ArrayList<Object> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        management = new Management(context);
    }

    @Override
    public int getItemViewType(int position) {


        if (dataArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (dataArray.get(position) instanceof DataObject) {

            DataObject dataObject = (DataObject) dataArray.get(position);

            if (dataObject.getDataType() == Constant.DATA_TYPE.FROM_CHAT)
                return FROM_CHAT_VIEW;
            else if (dataObject.getDataType() == Constant.DATA_TYPE.TO_CHAT)
                return TO_CHAT_VIEW;

        } else if (dataArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        }  else if (viewType == FROM_CHAT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.from_chat_item_layout, parent, false);
            viewHolder = new FromChatHolder(view);

        } else if (viewType == TO_CHAT_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_chat_item_layout, parent, false);
            viewHolder = new ToChatHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final StaggeredGridLayoutManager.LayoutParams layoutParams =
                new StaggeredGridLayoutManager.LayoutParams(
                        holder.itemView.getLayoutParams());

        if (holder instanceof ProgressHolder) {

            ProgressHolder lookUpHolder = (ProgressHolder) holder;
            layoutParams.setFullSpan(true);

        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

            layoutParams.setFullSpan(true);

        } else if (holder instanceof FromChatHolder) {

            DataObject chattingObject = (DataObject) dataArray.get(position);
            final FromChatHolder durationHolder = (FromChatHolder) holder;

            durationHolder.txtChat.setText(chattingObject.getChatting());
            durationHolder.txtDate.setText(chattingObject.getTime());
            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + chattingObject.getPicture())
                    .into(durationHolder.imageFrom);

          /*  if (recipeBotObject.isInvisible()) {
                durationHolder.imageFrom.setVisibility(View.INVISIBLE);
            } else {
                durationHolder.imageFrom.setVisibility(View.VISIBLE);
            }*/


        } else if (holder instanceof ToChatHolder) {

            DataObject chattingObject = (DataObject) dataArray.get(position);
            final ToChatHolder durationHolder = (ToChatHolder) holder;

            durationHolder.txtChat.setText(chattingObject.getChatting());
            GlideApp.with(context).load(Constant.ServerInformation.PICTURE_URL + chattingObject.getPicture())
                    .into(durationHolder.imageTo);

        }

        /*if (!(holder instanceof FromChatHolder) || !(holder instanceof ToChatHolder))
            holder.itemView.setLayoutParams(layoutParams);*/


    }

    @Override
    public int getItemCount() {
        return dataArray.size();

    }


    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }

    protected class FromChatHolder extends RecyclerView.ViewHolder {
        private TextView txtChat;
        private ImageView imageFrom;
        private TextView txtDate;

        public FromChatHolder(View view) {
            super(view);

            txtChat = (TextView) view.findViewById(R.id.txt_chat);
            txtDate = view.findViewById(R.id.txt_date);
            imageFrom = view.findViewById(R.id.image_from);

        }
    }

    protected class ToChatHolder extends RecyclerView.ViewHolder {
        private TextView txtChat;
        private ImageView imageTo;

        public ToChatHolder(View view) {
            super(view);

            txtChat = (TextView) view.findViewById(R.id.txt_chat);
            imageTo = view.findViewById(R.id.image_to);

        }
    }


}
