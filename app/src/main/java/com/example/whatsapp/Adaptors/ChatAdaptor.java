package com.example.whatsapp.Adaptors;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;

public class ChatAdaptor {

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg,receiverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.recievertext);
            receiverTime = itemView.findViewById(R.id.recievertime);
        }
    }
}
