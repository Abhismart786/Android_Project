//package com.example.whatsapp.Adaptors;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.whatsapp.ChatDetailActivity;
//import com.example.whatsapp.Models.MessageModel;
//import com.example.whatsapp.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//public class ChatAdaptor extends RecyclerView.Adapter {
//
//    ArrayList<MessageModel> messageModels;
//    Context context;
//    int SENDER_VIEW_TYPE = 1;
//    int RECEIVER_VIEW_TYPE = 2;
//    int recId;
//    public ChatAdaptor(ArrayList<MessageModel> messageModels, Context context) {
//        this.messageModels = messageModels;
//        this.context = context;
//    }
//
//    public ChatAdaptor(ArrayList<MessageModel> messageModels, ChatDetailActivity chatDetailActivity, String receiveId) {
//    }
//
//    public ChatAdaptor(ArrayList<MessageModel> messageModels, ChatDetailActivity chatDetailActivity, String senderRoom, String receiverRoom) {
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       if(viewType == SENDER_VIEW_TYPE)
//       {
//           View view = LayoutInflater.from(context).inflate(R.layout.samplesender,parent,false);
//           return new SenderViewHolder(view);
//       }
//        else {
//           View view = LayoutInflater.from(context).inflate(R.layout.samplereciever,parent,false);
//           return new RecieverViewHolder(view);
//       }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
//        {
//            return SENDER_VIEW_TYPE;
//        }
//        else {
//            return RECEIVER_VIEW_TYPE;
//        }
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        MessageModel messageModel = messageModels.get(position);
//        // function for delete message
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AlertDialog.Builder(context)
//                        .setTitle("Delete")
//                        .setMessage("Delete Message?")
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
//                                database.getReference().child("chats").child(senderRoom)
//                                        .child(messageModel.getMessageId()).setValue(null);
//
//                            }
//                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();
//            }
//        });
//        if (holder.getClass() == SenderViewHolder.class)
//        {
//            ((SenderViewHolder)holder).SenderMsg.setText(messageModel.getMessage());
//        }
//        else {
//            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
//        }
//    }
//
//    public ChatAdaptor(int recId) {
//        this.recId = recId;
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageModels.size();
//    }
//
//    public class RecieverViewHolder extends RecyclerView.ViewHolder{
//
//        TextView receiverMsg,receiverTime;
//
//        public RecieverViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            receiverMsg = itemView.findViewById(R.id.recievertext);
//            receiverTime = itemView.findViewById(R.id.recievertime);
//        }
//    }
//    public class SenderViewHolder extends RecyclerView.ViewHolder {
//
//        TextView SenderMsg, SenderTime;
//
//        public SenderViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            SenderMsg = itemView.findViewById(R.id.senderText);
//            SenderTime = itemView.findViewById(R.id.senderTime);
//        }
//    }
//}
package com.example.whatsapp.Adaptors;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessageModel;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdaptor extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    String senderRoom, receiverRoom;

    // Constructor with senderRoom and receiverRoom
    public ChatAdaptor(ArrayList<MessageModel> messageModels, Context context, String senderRoom, String receiverRoom) {
        this.messageModels = messageModels;
        this.context = context;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.samplesender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.samplereciever, parent, false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Delete Message?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                // Delete message from both sender and receiver rooms
                                database.getReference().child("chats")
                                        .child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);

                                // Remove from the local list and update the UI
                                messageModels.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).SenderMsg.setText(messageModel.getMessage());
        } else {
            ((RecieverViewHolder) holder).receiverMsg.setText(messageModel.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMsg, receiverTime;

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
