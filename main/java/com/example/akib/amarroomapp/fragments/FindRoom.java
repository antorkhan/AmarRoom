package com.example.akib.amarroomapp.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.akib.amarroomapp.R;
import com.example.akib.amarroomapp.db.GlobalData;
import com.example.akib.amarroomapp.models.Posts;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class FindRoom extends Fragment implements View.OnClickListener
{

    float X1,X2,Y1,Y2;
    Posts post;
    View v;
    Button nextButton,callButton;
    ImageView imageView;
    TextView roomDetails;
    public static String phoneNum3;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_find_room, container, false);
        nextButton=v.findViewById(R.id.nextButton);
        callButton=v.findViewById(R.id.callButton);
        nextButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
        imageView=v.findViewById(R.id.advertiseImage);
        roomDetails=v.findViewById(R.id.roomDetails);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        showARoom();




        return v;
    }
    final GestureDetector gesture = new GestureDetector(getActivity(),
            new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                  //  Log.i(SyncStateContract.Constants.APP_TAG, "onFling has been called!");
                    Log.d("antor","xxx");
                    final int SWIPE_MIN_DISTANCE = 50;
                    final int SWIPE_MAX_OFF_PATH = 250;
                    final int SWIPE_THRESHOLD_VELOCITY = 200;
                    try {
                        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                            return false;
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            showARoom();

                            Log.d("antor","anything");
                      //      Log.i(SyncStateContract.Constants.APP_TAG, "Right to Left");
                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            callUser();
                            Log.d("antor","left to ring");
                    //        Log.i(SyncStateContract.Constants.APP_TAG, "Left to Right");
                        }
                    } catch (Exception e) {
                        // nothing
                    }
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            });


//    @Override
//    public boolean onTouch(View v, MotionEvent motionEvent) {
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                X1 = motionEvent.getX();
//                Y1 = motionEvent.getY();
//
//
//                break;
//
//            case MotionEvent.ACTION_UP:
//                X2 = motionEvent.getX();
//                Y2 = motionEvent.getY();
//
//                if (X1 > X2) {
//                    Toast.makeText(getContext(), "swripe right", Toast.LENGTH_SHORT).show();
//                    showARoom();
//                    Log.d("antor", "show a SWipe Right");
//                }
//                if (X1 < X2) {
//                    Toast.makeText(getContext(), "swipe left", Toast.LENGTH_SHORT).show();
//                    showARoom();
//                    Log.d("antor", "show a SWipe Left");
//                }
//
//                break;
//        }
//        return false;
//    }
    public void showARoom(){

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, "http://antor.info/AmarRoom/getemployee.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject obj = response.getJSONObject(0);
                    post = new Posts(obj.getInt("post_id"),obj.getInt("user_id"),obj.getInt("rent"),obj.getString("image"), obj.getString("address"),obj.getString("note"));
                }

                catch (JSONException e) {
                    e.printStackTrace();

                }



                String imgurl=post.getImage();


                Picasso.get().load(imgurl).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView);
                roomDetails.setText(post.getAddress()+","+post.getRent()+"\n About: "+post.getNote());



                // Toast.makeText(getActivity().getApplicationContext(), arrEmployee.get(0).getName(), Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "No internet available", Toast.LENGTH_LONG).show();
            }
        });


        GlobalData.getInstance().addToRequestQueue(arrayRequest);




    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextButton:
                showARoom();
                Log.d("antor","show a room called");
                break;
            case R.id.callButton:
                callUser();
                break;

        }

    }



    private void callUser(){
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "01711273727"));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "01711273727"));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}

