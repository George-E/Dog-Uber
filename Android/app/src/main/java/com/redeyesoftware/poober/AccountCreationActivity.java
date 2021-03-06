package com.redeyesoftware.poober;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AccountCreationActivity extends AppCompatActivity {


    EditText usernameText;
    EditText passwordText;
    EditText confirmPasswordText;
    //variables
    String username = null;
    String password = null;
    String confirmedPassword = null;
    static int MinNameLength = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_account_creation);


        Button submitButton = (Button) findViewById(R.id.Confirm);
        usernameText = (EditText) findViewById(R.id.UsernameBox);
        passwordText = (EditText) findViewById(R.id.PasswordBox);
        confirmPasswordText = (EditText) findViewById(R.id.ConfirmPasswordBox);

        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (usernameText.length()<= MinNameLength){
                    usernameText.setError("Minimum Length is " + MinNameLength + " characters!");
                }

            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(passwordText.length() < MinNameLength){
                    passwordText.setError("Minimum length is " + MinNameLength + " characters!");
                }

            }
        });
        confirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!confirmPasswordText.getText().toString().equals(passwordText.getText().toString())){
                    confirmPasswordText.setError("Passwords Must Match");
                }
            }
        });

        //submit button
        /*submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save all of the stuff entered
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                Log.d("DEBUG1", passwordText.toString());

            }
        });*/


    }

    public void OnButtonClick(View view){
        //view.animate();
        Log.d("DEBUG", passwordText.toString());

        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
        //do stuff
    }


}
