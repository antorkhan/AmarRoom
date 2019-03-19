package com.example.akib.amarroomapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.example.akib.amarroomapp.db.GlobalData;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.akib.amarroomapp.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userName,Password;
    public static String usernameInput,passwordInput;
    public static boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
//        String name = sharedPreferences.getString(USERNAMEKEY, "Not found!");
    }

    public void GoPressed(View view)
    {
        userName=findViewById(R.id.userName);
        Password=findViewById(R.id.passWord);

         usernameInput=userName.getText().toString();
         passwordInput=Password.getText().toString();
         loginUser();



    }

    public void loginUser() {


        String url="http://antor.info/AmarRoom/getUser.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {

                            JSONObject obj = new JSONObject(response);

                            int a=Integer.parseInt(obj.getString("response"));
                           // Log.d("antor",Integer.toString(a+a));
                            if(a==1 ){
                                flag=true;
                                Log.d("antor","true");
                                SharedPreferences sharedPref = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("phoneShared",usernameInput);
                                editor.apply();

                                Intent DashbordIntent = new Intent(LoginActivity.this,DashbordActivity.class);
                                startActivity(DashbordIntent);
                            }
                            else{
                                flag=false;
                                Log.d("antor","false");
                                Intent DashbordIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                                startActivity(DashbordIntent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("antor",error.toString());

                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone",LoginActivity.this.usernameInput);
                params.put("password",LoginActivity.this.passwordInput);


                return params;
            }
        };

        GlobalData.getInstance().addToRequestQueue(postRequest);



    }
}
