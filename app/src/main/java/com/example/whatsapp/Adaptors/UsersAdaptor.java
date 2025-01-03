package com.example.whatsapp.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.ChatDetailActivity;
import com.example.whatsapp.Models.Users;
import com.example.whatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdaptor extends RecyclerView.Adapter<UsersAdaptor.ViewHolder> {
    ArrayList<Users> list;
    Context context;

    public UsersAdaptor(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.avtar).into(holder.image);
        holder.UserName.setText(users.getUserName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(context, ChatDetailActivity.class);
                    intent.putExtra("userId", users.getUserId());
                    intent.putExtra("profilePic", users.getProfilepic());
                    intent.putExtra("userName", users.getUserName());
                    context.startActivity(intent);


//
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
       ImageView image;
       TextView UserName,lastMessage;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           image = itemView.findViewById(R.id.profile_image);
           UserName = itemView.findViewById(R.id.Username);
          lastMessage = itemView.findViewById(R.id.lastMessage);
       }
   }
}
