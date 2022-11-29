package algonquin.cst2335.dai00047;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import algonquin.cst2335.dai00047.R;
import algonquin.cst2335.dai00047.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.dai00047.databinding.SentMessageBinding;
import data.ChatMessage;
import data.ChatMessageDAO;
import data.ChatRoomViewModel;
import data.MessageDatabase;
import data.MessageDetailsFragment;



public class ChatRoom extends AppCompatActivity {

    MessageDatabase db;
    ActivityChatRoomBinding binding;
    //ArrayList<String> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages1 = new ArrayList<>();
    //ArrayList<String> messages;
    RecyclerView.Adapter <MyRowHolder> myAdapter;
    ChatMessageDAO mDAO;
    int position;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch( item.getItemId() )
        {
            case R.id.item_1:

                //put ChatMessage deletion code here. If you select this item, you should show the alert dialog
                //asking if the user wants to delete this message.
                ChatMessage selected= chatModel.selectedMessage.getValue();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message:" + selected.getMessage());
                builder.setTitle("Question:");

                builder.setNegativeButton("No", (a, b) -> {});
                builder.setPositiveButton("Yes", (a, b) -> {

                    Snackbar.make(binding.getRoot(),"You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk ->{
                                Executor thread = Executors.newSingleThreadExecutor();
                                thread.execute(() -> {
                                    mDAO.insertMessage(selected);});
                                myAdapter.notifyItemInserted(position);
                                chatModel.messages.getValue().add(selected);
                            }).show();
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        mDAO.deleteMessage(selected);
                        chatModel.messages.getValue().remove(position);
                        runOnUiThread(() ->{
                            myAdapter.notifyItemRemoved(position);
                            this.onBackPressed();
                        });
                    });

                });
                builder.create().show();
                Toast.makeText(this,"You clicked on the delete pail", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_2:
                Snackbar.make(binding.myToolbar,"Version 1.0,created by Jingjing Lin", BaseTransientBottomBar.LENGTH_LONG).show();

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages1 = chatModel.messages.getValue();

        if (messages1 == null) {
            chatModel.messages.postValue(messages1 = new ArrayList<>());
        }


        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage obj1 = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, true);

//            ChatMessage obj1 = new ChatMessage();
//            obj1.setMessage( binding.textInput.getText().toString() );
            //chatModel.messages.getValue().add(obj1);
            messages1.add(obj1);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            { mDAO.insertMessage(obj1);
                //Once you get the data from database
            });
            myAdapter.notifyItemInserted(messages1.size() - 1);
            binding.textInput.setText("");

//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute( () -> {
//                mDAO.insertMessage(obj1);
//            });
        });

        binding.receivedButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage obj1 = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, false);
            messages1.add(obj1);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            { mDAO.insertMessage(obj1);
                //Once you get the data from database
            });
            myAdapter.notifyItemInserted(messages1.size() - 1);
            binding.textInput.setText("");


        });

        //setContentView(R.layout.activity_chat_room);
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
//                View room = getLayoutInflater().inflate(R.layout.sent_message,parent, false);
//
//                return new MyRowHolder( binding.getRoot());
                View root;
                if (viewType == 0) {
                    // inflate
                    root = getLayoutInflater().inflate(R.layout.sent_message, parent, false);
                } else {
                    root = getLayoutInflater().inflate(R.layout.recieve_message, parent, false);
                }
                // pass the root to constructor
                return new MyRowHolder(root);


            }
            //
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                holder.messageText.setText("");
                //holder.timeText.setText("");

                ChatMessage obj = messages1.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());


            }


            @Override
            public int getItemCount() {
                return messages1.size();
            }

            public int getItemViewType(int position) {
                ChatMessage obj = messages1.get(position);
                if (obj.getIsSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        //MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        mDAO = db.cmDAO();
        //messages1 = chatModel.messages.getValue();
        if (messages1 == null) {
            chatModel.messages.setValue(messages1 = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages1.addAll(mDAO.getAllMessages()); //Once you get the data from database

                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView


            });
        }
        chatModel.selectedMessage.observe(this, (newMessageValue) -> {

            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);

            tx.replace(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack("");
            tx.commit();



        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;


        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages1.get(position);

                chatModel.selectedMessage.postValue(selected);
//                // ChatMessage thisMessage = messages.get(position);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
//                     builder.setMessage("Do you want to delete the message: " + messageText.getText())
//                        .setTitle("Question:")
//                        .setNegativeButton("No", (dialog, cl) -> {
//                        })
//                        .setPositiveButton("Yes", (dialog, cl) -> {
//                            ChatMessage m = messages1.get(position);
//                            messages1.remove(position);
//                            myAdapter.notifyItemRemoved(position);
//                           Snackbar.make(messageText, "You deleted message #" +position,
//                                              Snackbar.LENGTH_LONG).setAction("Undo",c ->{
//                                          messages1.add(position, m);
//
//
//                              myAdapter.notifyItemInserted(position);
//                           }).show();
//
//
//                        })
//                        .create().show();

            });

            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);


        }
    }
}
















