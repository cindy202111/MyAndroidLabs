package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.dai00047.R;
import algonquin.cst2335.dai00047.databinding.ActivityMainBinding;
import data.MainViewModel;

/** @author Yongli Dai
 * @version 1.0
 * @since October 26, 2022
 */

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    /** This holds the login button reference     */
    protected Button lButton;
    protected TextView textView;

    /** This holds the editText reference for login */
    protected EditText loginText;


    boolean foundUpperCase = false;
    boolean foundLowerCase = false;
    boolean foundNumber = false;
    boolean foundSpecial = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState); //calls parent onCreate()
       setContentView(R.layout.activity_main);
        lButton = findViewById(R.id.button);
        loginText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        stringHas123(loginText.getText().toString());


        lButton.setOnClickListener(clk ->

        {
            String password = loginText.getText().toString();

            if (checkPasswordComplexity(password)) {

                textView.setText("Your password meets the requirements.");

            } else if (!foundUpperCase) {
                Toast.makeText(this,"Your are missing an upper case letter",Toast.LENGTH_LONG).show();
                textView.setText("You shall not pass!");

            } else if (!foundLowerCase) {
                Toast.makeText(this,"Your are missing a lower case letter",Toast.LENGTH_LONG).show();
                textView.setText("You shall not pass!");

            } else if (!foundNumber) {
                Toast.makeText(this,"You are missing a digit",Toast.LENGTH_LONG).show();
                textView.setText("You shall not pass!");

            } else if (!foundSpecial) {
                Toast.makeText(this,"You are missing a special character",Toast.LENGTH_LONG).show();
                textView.setText("You shall not pass!");
            }



        });


    }

    /** This function checks if s has the string "123" somewhere in it
     *
     * @param s Is the string that we are checking for "123"
     * @return Returns true if s has "123", false otherwise
     */

    public boolean stringHas123(String s){
       return s.contains("123");
    }




    boolean isSpecialCharacter(char c){
        String special = "#$%^&*!@?";
        boolean found = false;
        if (special.contains(String.valueOf(c))){
            found = true;
        }
        return found;

        }






    /** This function
     *
     * @param pw The String object that we are checking
     * @return Returns true if ....
     */

     public boolean checkPasswordComplexity(String pw) {

    //     boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

     //    foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;


        // Aa check = loginText.getText().toString();


         for (int i = 0; i < pw.length(); i++) {

             char c = pw.charAt(i);

            if(Character.isDigit(c)){foundNumber=true;}
            else if(Character.isLowerCase(c)){foundLowerCase=true; }
            else if(Character.isUpperCase(c)){ foundUpperCase=true;}
            else if(isSpecialCharacter(c)){foundSpecial=true;}

         }

         if (!foundUpperCase) {
           //  textView.setText("Your are missing an upper case letter");
             return false;
         }
         else if(!foundLowerCase){
          //   textView.setText("You are missing a lower case letter");
             return false;
         }

         else if(!foundNumber ){
         //    textView.setText("You are missing a digit");
             return false;
         }
         else if(!foundSpecial){
         //    textView.setText("You are missing special ");
             return false;
         }
         else {

             return true;
         }



     }














      //  model = new ViewModelProvider(this).get(MainViewModel.class);
      //  variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
      //  setContentView(variableBinding.getRoot()); // loads xml on screen


//        variableBinding.mybutton.setOnClickListener(click ->{
//            model.editString = variableBinding.myedittext.getText().toString();
//            variableBinding.myedittext.setText("Your edit text has: "+model.editString);
//
//
//        });
















}