//package com.example.whatsapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.whatsapp.Adaptors.ChatAdaptor;
//import com.example.whatsapp.Models.MessageModel;
//import com.example.whatsapp.databinding.ActivityChatDetailBinding;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//public class ChatDetailActivity extends AppCompatActivity {
//    ActivityChatDetailBinding binding;
//    FirebaseDatabase database;
//    FirebaseAuth auth;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Initialize binding before using it
//        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Hide the action bar
//        getSupportActionBar().hide();
//
//        // Firebase initialization
//        database = FirebaseDatabase.getInstance();
//        auth = FirebaseAuth.getInstance();
//
//
//        final String senderId = auth.getUid();
//        String receiveId = getIntent().getStringExtra("userId");
//        String userName = getIntent().getStringExtra("userName");
//        String profilePic = getIntent().getStringExtra("profilePic");
//
//
//        binding.userName.setText(userName);
//        Picasso.get().load(profilePic).placeholder(R.drawable.avtar).into(binding.profileImage);
//
//        binding.backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChatDetailActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        final ArrayList<MessageModel> messageModels = new ArrayList<>();
//        final ChatAdaptor chatAdaptor = new ChatAdaptor(messageModels,this,receiveId);
//        binding.chatRecyclerView.setAdapter(chatAdaptor);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        binding.chatRecyclerView.setLayoutManager(layoutManager);
//        final String senderRoom = senderId+receiveId;
//        final String receiverRoom = receiveId+senderId;
//        database.getReference().child("chats")
//                        .child(senderRoom)
//                        .addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                messageModels.clear();
//                                for(DataSnapshot snapshot1:snapshot.getChildren())
//                                {
//                                    MessageModel model = snapshot1.getValue(MessageModel.class);
//                                    model.setMessageId(snapshot1.getKey());
//
//                                    messageModels.add(model);
//                                }
//                                    chatAdaptor.notifyDataSetChanged();
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                                });
//        binding.send.setOnClickListener(new View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View view) {
//              String message = binding.etMessage.getText().toString();
//              final MessageModel model = new MessageModel(senderId,message);
//              model.setTimestamp(new Date().getTime());
//              binding.etMessage.setText("");
//              database.getReference().child("chats")
//                      .child(senderRoom)
//                      .push()
//                      .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                          @Override
//                          public void onSuccess(Void aVoid) {
//
//                              database.getReference().child("chats")
//                                      .child(receiverRoom)
//                                      .push()
//                                      .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                          @Override
//                                          public void onSuccess(Void aVoid) {
//
//                                          }
//                                      });
//                          }
//                      });
//            }
//        });
//    }
//
//}
package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsapp.Adaptors.ChatAdaptor;
import com.example.whatsapp.Models.MessageModel;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding before using it
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hide the action bar
        getSupportActionBar().hide();

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        final String receiveId = getIntent().getStringExtra("userId");
        final String userName = getIntent().getStringExtra("userName");
        final String profilePic = getIntent().getStringExtra("profilePic");

        // Set the user data in the view
        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avtar).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        // Define senderRoom and receiverRoom
        final String senderRoom = senderId + receiveId;
        final String receiverRoom = receiveId + senderId;

        // Pass the rooms to the adapter constructor
        final ChatAdaptor chatAdaptor = new ChatAdaptor(messageModels, this, senderRoom, receiverRoom);
        binding.chatRecyclerView.setAdapter(chatAdaptor);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        // Fetch messages from the senderRoom
        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        chatAdaptor.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        // Sending a new message
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                // Push the message to both sender and receiver rooms
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model);
                            }
                        });
            }
        });
    }
}
