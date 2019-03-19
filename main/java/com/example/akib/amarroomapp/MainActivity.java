  package com.example.akib.amarroomapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

  public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void JoinPressed(View view)
    {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
        //Toast.makeText(MainActivity.this, "Sign up Pressed", Toast.LENGTH_SHORT).show();
    }

      public void LoginPressed(View view)
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
       // Toast.makeText(MainActivity.this, "Log in Pressed", Toast.LENGTH_SHORT).show();
    }

}
