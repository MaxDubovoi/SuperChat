package com.internship.supercoders.superchat.chats.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.chat.ChatActivity;
import com.internship.supercoders.superchat.chat.service.SmackService;
import com.internship.supercoders.superchat.chats.UserActionsListener;
import com.internship.supercoders.superchat.chats.chat_model.ChatModel;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.dialog.DialogData;
import com.vanniktech.emoji.EmojiTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChatsRecyclerAdapter extends RecyclerView.Adapter<ChatsRecyclerAdapter.CustomViewHolder> {
    private List<DialogData> chatsList;
    private UserActionsListener mItemListener;
    DBMethods dbMethods;

    public ChatsRecyclerAdapter(List<DialogData> chatsList, UserActionsListener mItemListener, DBMethods dbMethods) {
        this.mItemListener =mItemListener;
        this.chatsList = chatsList;
        this.dbMethods = dbMethods;
    }


    @Override
    public ChatsRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_item, parent, false);

        return new ChatsRecyclerAdapter.CustomViewHolder(itemView, mItemListener);
    }

    @Override
    public void onBindViewHolder(final ChatsRecyclerAdapter.CustomViewHolder holder, int position) {
        DialogData model = chatsList.get(position);
        holder.groupName.setText(model.getName());
        holder.message.setText(model.getLastMessage());
        dbMethods.getUserEmail(Integer.parseInt(model.getLastMessageUserId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(email -> holder.lastMessageUserName.setText(email),
                        error -> Log.d("stas", " dbMethods.getUserEmail error = " + error.getMessage()));

        holder.cardView.setOnClickListener(v -> {
            Intent intent =new Intent(holder.cardView.getContext(), SmackService.class);
            intent.putExtra("id", model.getChatId());
            holder.cardView.getContext().startService(intent);
            Intent intent1 = new Intent(holder.cardView.getContext(), ChatActivity.class);
            intent1.putExtra("chatId", model.getChatId());





            holder.cardView.getContext().startActivity(intent1);
        });


    }

    public void loadChats(List<DialogData> chatsList){
        this.chatsList.addAll(chatsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView userPhoto;
        TextView groupName;
        TextView lastMessageUserName;
        EmojiTextView message;
        TextView date;
        CardView cardView;
        private UserActionsListener mItemListener;

        public CustomViewHolder(View itemView, UserActionsListener listener) {
            super(itemView);
            mItemListener = listener;
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            userPhoto = (CircleImageView) itemView.findViewById(R.id.photo);
            groupName = (TextView) itemView.findViewById(R.id.groupName);
            lastMessageUserName = (TextView) itemView.findViewById(R.id.userName);
            message = (EmojiTextView) itemView.findViewById(R.id.lastMessage);
            date = (TextView) itemView.findViewById(R.id.date);


        }

        @Override
        public void onClick(View view) {
         mItemListener.openChat();
        }
    }
}