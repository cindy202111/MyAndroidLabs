package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.dai00047.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); //calls parent onCreate()
       // setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MainViewModel.class);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); // loads xml on screen


//        variableBinding.mybutton.setOnClickListener(click ->{
//            model.editString = variableBinding.myedittext.getText().toString();
//            variableBinding.myedittext.setText("Your edit text has: "+model.editString);
//
//
//        });

        TextView mytext = variableBinding.textview;

        Button btn = variableBinding.mybutton;

        EditText myedit = variableBinding.myedittext;

       btn.setOnClickListener(click ->{
          model.editString.postValue(variableBinding.myedittext.getText().toString());

       });

       // variableBinding.mybutton.setOnClickListener(    ( vw ) -> {
        // mytext.setText("Your edit text has: " + editString);  }   );


        model.editString.observe(this,s ->{
           variableBinding.textview.setText("Your edit text has" +s);
        });

            model.isSelected.observe(this,isChecked ->{
                variableBinding.checkbox.setChecked(isChecked);
                variableBinding.myswitch.setChecked(isChecked);
                variableBinding.myradio.setChecked(isChecked);


               




            });


         variableBinding.checkbox.setOnCheckedChangeListener( (button, isChecked)->{

             model.isSelected.postValue(isChecked);


             Toast.makeText(getApplicationContext(),
                     "You clicked on the " +  "checkbox" + " and it is now: "
                             + isChecked,Toast.LENGTH_SHORT).show();

         });


        variableBinding.myradio.setOnCheckedChangeListener( (button, isChecked)->{

            model.isSelected.postValue(isChecked);
            Toast.makeText(getApplicationContext(),
                    "You clicked on the " +  "radiobutton" + " and it is now: "
                            + isChecked,Toast.LENGTH_SHORT).show();

        });


        variableBinding.myswitch.setOnCheckedChangeListener( (button, isChecked)->{
            model.isSelected.postValue(isChecked);
            Toast.makeText(getApplicationContext(),
                    "You clicked on the " +  "myswitch" + " and it is now: "
                            + isChecked,Toast.LENGTH_SHORT).show();

        });

        variableBinding.myimagebutton.setOnClickListener( (click->{
            double width = variableBinding.myimagebutton.getWidth();
            double height = variableBinding.myimagebutton.getHeight();

            Toast.makeText(getApplicationContext(),
                            "The width = " + width + " and height = "
                            + height,Toast.LENGTH_SHORT).show();

        }));










    }












}