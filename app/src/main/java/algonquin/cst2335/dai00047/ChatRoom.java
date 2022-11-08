package algonquin.cst2335.dai00047;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import algonquin.cst2335.dai00047.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.dai00047.databinding.SentMessageBinding;
import data.ChatMessage;
import data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {


    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ArrayList<String> messages;

    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        binding.sendButton.setOnClickListener(click -> {
            chatModel.messages.getValue().add(binding.textInput.getText().toString());
            myAdapter.notifyItemInserted( messages.size()-1 );
            //clear the previous text:
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            chatModel.messages.getValue().add(binding.textInput.getText().toString());
            myAdapter.notifyItemChanged( messages.size()-1 );
            //clear the previous text:
            binding.textInput.setText("");
        });

        //Set a layout manager for the rows to be aligned vertically using only 1 column.
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(  binding.getRoot() );
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText(messages.get(position));
                holder.timeText.setText("");
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }



//    ActivityChatRoomBinding binding;
//  //  ArrayList<ChatMessage> messages;
//     ArrayList<String> messages;
//    ChatRoomViewModel chatModel;
//    private RecyclerView.Adapter myAdapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//       if(messages == null)
//        {
//         //     chatModel.messages.postValue( messages = new ArrayList<String>());
//           chatModel.messages.postValue( messages = new ArrayList<>());
//       }
//        messages = chatModel.messages.getValue();
//        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
//        super.onCreate(savedInstanceState);
//       // setContentView(R.layout.activity_chat_room2);
//        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
//        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
//          //boolean isSend = false;
//            @NonNull
//            @Override //given the view type, just load a MyRowHolder
//            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//               // SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
//                View root;
//               // if(viewType == 0) // 0 represent text on the right
//                    root = getLayoutInflater().inflate(R.layout.sent_message,parent,false);
//                //else
//                 //   root = getLayoutInflater().inflate(R.layout.recieve_message,parent,false);
//                //SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(),parent,false);
//
//
//                //line 2 is to pass the root to constructor
//                return new MyRowHolder(root);
//
//            }
//
//            @Override  // position is the row, starting with 0
//            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                //set the textView;
//                String thisRow = messages.get(position);
//                holder.messageText.setText((CharSequence) thisRow);
//               // holder.timeText.setText("");
//
//            }
//
//
//
//            @Override // hwo many rows are there
//            public int getItemCount() {
//                return messages.size();
//            }
//
//
//            @Override
//           public int getItemViewType(int position) {
//             //   String thisMessage = messages.get(position);
//            //   if(isSend){return 0;}
//              // return super.getItemViewType(position);
//                return 0;
//            }
//
//        });
//
//        binding.sendButton.setOnClickListener(click -> {
//           // messages.add(binding.textInput.getText().toString());
//          //  SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
//          //  String currentDateandTime = sdf.format(new Date());
//         //  ChatMessage chatMessage = new ChatMessage("",currentDateandTime, true);
//            String messageText = binding.textInput.getText().toString();
//            messages.add(messageText);
//          //  myAdapter.notifyItemInserted(messages.size()-1);
//            myAdapter.notifyItemInserted(messages.size()-1);
//             //clear the previous text:
//            binding.textInput.setText("");
//
//        });
//
//        binding.receiveButton.setOnClickListener(click -> {
//            //SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
//            //String currentDateandTime = sdf.format(new Date());
//            //ChatMessage chatMessage = new ChatMessage("",currentDateandTime, false);
//             String messageText = binding.textInput.getText().toString();
//             messages.add(messageText);
//           // myAdapter.notifyItemInserted(messages.size()-1);
//            myAdapter.notifyItemInserted(messages.size()-1);
//            //clear the previous text:
//            binding.textInput.setText("");
//
//        });
//
//
//    }
//
//    // represents a single row
//    static class MyRowHolder extends RecyclerView.ViewHolder{
//
//       TextView messageText;
//       TextView timeText;
//
//
//        public MyRowHolder(@NotNull View itemView){
//            super(itemView);
//            // find the message
//            messageText = itemView.findViewById(R.id.messageId);
//            //find the time
//            timeText = itemView.findViewById(R.id.timeId);
//        }
//
//
////        public MyRowHolder(@NonNull int id, View itemView ,String timeSent){
////            super(itemView);
////            this.messageText=itemView.findViewById(id);
////
////
////
////        }


    }















