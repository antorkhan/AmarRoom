package com.example.akib.amarroomapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.akib.amarroomapp.db.GlobalData;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText nameInput,emailInput,addressInput,passwordInput,confirmPasswordInput;
    public static String name,email,address,pass,confirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    public void validateJoin(View view) {
        nameInput=findViewById(R.id.nameInput);
        emailInput=findViewById(R.id.emailInput);
        addressInput=findViewById(R.id.addressInput);
        passwordInput=findViewById(R.id.passwordInput);
        confirmPasswordInput=findViewById(R.id.confirmPasswordInput);

        name=nameInput.getText().toString();
        email=emailInput.getText().toString();
        address=addressInput.getText().toString();
        pass=passwordInput.getText().toString();
        confirmPass=confirmPasswordInput.getText().toString();

        if(pass.equals(confirmPass)){
            registerUser(name,email,address,pass);
        }
        else{
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
        }



    }
    public void registerUser(String name,String address,String email,String password) {


        String url="http://antor.info/AmarRoom/userRegistration.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",SignUpActivity.this.name);
                params.put("email", SignUpActivity.this.email);
                params.put("address", SignUpActivity.this.address);
                params.put("password", SignUpActivity.this.pass);

                return params;
            }
        };

        GlobalData.getInstance().addToRequestQueue(postRequest);



    }

}
