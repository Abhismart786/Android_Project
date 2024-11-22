package com.example.whatsapp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessageModel;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdaptor extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdaptor(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType == SENDER_VIEW_TYPE)
       {
           View view = LayoutInflater.from(context).inflate(R.layout.samplesender,parent,false);
           return new SenderViewHolder(view);
       }
        else {
           View view = LayoutInflater.from(context).inflate(R.layout.samplereciever,parent,false);
           return new RecieverViewHolder(view);
       }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);
        if (holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).SenderMsg.setText(messageModel.getMessage());
        }
        else {
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg,receiverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.recievertext);
            receiverTime = itemView.findViewById(R.id.recievertime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView SenderMsg, SenderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            SenderMsg = itemView.findViewById(R.id.senderText);
            SenderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
