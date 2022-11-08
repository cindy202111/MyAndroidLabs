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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.dai00047.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.dai00047.databinding.SentMessageBinding;
import data.ChatMessage;
import data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

   // ArrayList<String> allMessages;

    ArrayList<ChatMessage> chatList = new ArrayList<>();
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    RecyclerView.Adapter<MyRowHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));//saying it's a vertical list
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);


        chatList = chatModel.messages.getValue();


        if(chatList == null) {
            chatModel.messages.postValue( chatList = new ArrayList<>());
        }
ccc
        binding.sendButton.setOnClickListener(click -> {

          //  String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");//  String currentDateandTime = sdf.format(new Date());
           String messageText = binding.textInput.getText().toString();

            String currentDateandTime = sdf.format(new Date());
           ChatMessage chatMessage = new ChatMessage(messageText,currentDateandTime, true);
           chatList.add(chatMessage);
            //refresh the list:
            adapter.notifyItemInserted(chatList.size() - 1); //wants to know which position has changed
            binding.textInput.setText("");//remove what was there
        });


        binding.receiveButton.setOnClickListener(click -> {

            //  String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");//  String currentDateandTime = sdf.format(new Date());
            String messageText = binding.textInput.getText().toString();

            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(messageText,currentDateandTime, false);
            chatList.add(chatMessage);
            //refresh the list:
            adapter.notifyItemInserted(chatList.size() - 1); //wants to know which position has changed
            binding.textInput.setText("");//remove what was there
        });



        //you must create this class below, extend RecyclerView.ViewHolder
        binding.recycleView.setAdapter(adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            //given the view type, just load a MyRowHolder      //what layout do you want?
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                //line 1 is to inflate,
                View root;
                if(viewType == 0){
                    root = getLayoutInflater().inflate(R.layout.sent_message, parent, false);
                }
               else{
                    root = getLayoutInflater().inflate(R.layout.recieve_message, parent, false);
                }


                //line 2 is to pass the root to constructor
                return new MyRowHolder(root);

            }


            @Override
            public int getItemViewType(int position) {
               // return 0; //0 represents send, text on the left
                   if(chatList.get(position).getIsSentButton()){return 0;}
                   else{
                       return 1;
                   }


            }

            @Override
            //position is the row, starting with 0
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                //here is where we set the textViews:
                //what goes in row at position?
                ChatMessage thisRow = chatList.get(position);// get what goes in this row

                //set the textView:
                holder.messageText.setText(thisRow.getMessage());// that's all this does
                holder.timeText.setText(thisRow.getTimeSent());

            }

            @Override  // how many rows are there?
            public int getItemCount() {
                return chatList.size();
            }
        });  //An adapter is an object that feeds data to the list
    }

    //represents a single row
    class MyRowHolder extends RecyclerView.ViewHolder {

        TextView timeText;
        TextView messageText;

        //itemVIew is the ConstraintLayout that has 2 text subitems
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);//find the Message
            timeText = itemView.findViewById(R.id.timeText);//find the time
        }
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
//String messageText = binding.textInput.getText().toString();
//         //  ChatMessage chatMessage = new ChatMessage(messageText,currentDateandTime, true);
//
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


















