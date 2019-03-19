package com.example.akib.amarroomapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.akib.amarroomapp.DashbordActivity;
import com.example.akib.amarroomapp.LoginActivity;
import com.example.akib.amarroomapp.R;
import com.example.akib.amarroomapp.SignUpActivity;
import com.example.akib.amarroomapp.db.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends Fragment
{
    View v;
    TextView nameField;
    public static int user_id;
    public static String phoneNum;
    public static String add,rent;
    public static EditText addField,rentField;
    public static boolean flag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phoneNum=sharedPref.getString("phoneShared","null");

        nameField.setText(phoneNum);
        addField=v.findViewById(R.id.addressChangeInput);
        rentField=v.findViewById(R.id.rentChangeInput);
        rentField.setText("why this");

        return v;
    }
    public void EdiProfile() {


        String url="http://antor.info/AmarRoom/getUserFull.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {

                            JSONObject obj = new JSONObject(response);
                            String s=obj.getString("response");
                           // String s=obj.getString("response");
                            Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                            Log.d("antor",s);


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
                params.put("phone",EditProfile.this.phoneNum);



                return params;
            }
        };

        GlobalData.getInstance().addToRequestQueue(postRequest);



    }


}
