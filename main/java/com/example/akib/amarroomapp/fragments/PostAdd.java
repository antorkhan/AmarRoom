package com.example.akib.amarroomapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.akib.amarroomapp.db.GlobalData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.akib.amarroomapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class PostAdd extends Fragment implements View.OnClickListener
{
 //   ImageButton galleryButton;
    ImageView imageView;
    public static EditText rentField,addressField,noteField;
    private final int IMG_REQUEST=1;
    private Bitmap bitmap;
    public static String phoneNum2;
    private String UploadUrl="http://antor.info/AmarRoom/postAdd.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_post_add, container, false);
        Button b=v.findViewById(R.id.postButton);
        rentField=v.findViewById(R.id.rentField);
        addressField=v.findViewById(R.id.addressField);
        noteField=v.findViewById(R.id.noteField);
        ImageButton galleryButton=v.findViewById(R.id.galleryButton);
        ImageButton cameraButton=v.findViewById(R.id.cameraButton);
        galleryButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        b.setOnClickListener(this);
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phoneNum2=sharedPref.getString("phoneShared","null");
        imageView=v.findViewById(R.id.roomImage);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postButton:
                uploadImage();
                break;
            case R.id.galleryButton:
                selectImage();
                break;
            case R.id.cameraButton:
                Toast.makeText(getContext(),"PAIN",Toast.LENGTH_SHORT).show();
                break;
        }

    }
    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null){
            Uri path=data.getData();

            try{

                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);


            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }
    private void uploadImage(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(response);
                            String Response=jsonObject.getString("response");
                            //Log.d("antor",Response);
                            Toast.makeText(getContext(),Response,Toast.LENGTH_SHORT).show();
                            imageView.setImageResource(0);
                            imageView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("antor",error.toString());
                Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("address",PostAdd.this.addressField.getText().toString());
                params.put("rent",PostAdd.this.rentField.getText().toString());
                params.put("note",PostAdd.this.noteField.getText().toString());
                params.put("phone",PostAdd.this.phoneNum2);
                //Log.d("antor",imageToString(bitmap));
                params.put("image",PostAdd.this.imageToString(bitmap));
                return params;

            }
        };
        GlobalData.getInstance().addToRequestQueue(stringRequest);
        //Log.d("antor","globaldata fired");


    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
