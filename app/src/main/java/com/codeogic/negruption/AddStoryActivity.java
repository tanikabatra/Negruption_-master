package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddStoryActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.editTextStoryTitle)
    EditText txtStoryTitle;

    @InjectView(R.id.editTextStory)
    EditText txtStoryDesc;

    @InjectView(R.id.spinnerCities)
    Spinner spinnerMainCities;

    @InjectView(R.id.spinnerStates)
    Spinner spinnerMainStates;

    @InjectView(R.id.spinnerDepartment)
    Spinner spinnerDepartment;

    @InjectView(R.id.editTextStoryTitle)
    EditText editTextStoryTitle;

    @InjectView(R.id.editTextStory)
    EditText editTextAddStory;

    @InjectView(R.id.chkAnym)
    CheckBox chck;


    @InjectView(R.id.textViewHonestOfficer)
    TextView textViewHonestOfficer;

    @InjectView(R.id.buttonNext1)
    Button btnNext;

    String stateName;
    ArrayAdapter<String> arrayAdapterStates,arrayAdapterCities,arrayAdapterDepartment;

    String selectedPath;

    StoryBean storyBean;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String privacy = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);


        ButterKnife.inject(this);
        setStates();
         setDepartment();
        btnNext.setOnClickListener(this);

        storyBean = new StoryBean();

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");

        chck.setOnCheckedChangeListener(this);

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);

        editor = sharedPreferences.edit();

        userId = sharedPreferences.getInt(Util.PREFS_KEYUSERID,0);

        textViewHonestOfficer.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {



       if (v.getId()==R.id.buttonNext1) {
           storyBean.setStoryTitle(txtStoryTitle.getText().toString().trim());
           storyBean.setStoryDesc(txtStoryDesc.getText().toString().trim());

           progressDialog.show();

           uploadData();



       }
       else if(v.getId()== R.id.textViewHonestOfficer){
           Intent intent = new Intent(this,HonestStoryActivity.class);
           startActivity(intent);
       }

    }






    public void setDepartment(){

        arrayAdapterDepartment=new ArrayAdapter<String>(AddStoryActivity.this,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterDepartment.add("--  Which Department ? --");
        arrayAdapterDepartment.add("Airports");
        arrayAdapterDepartment.add("Banking");
        arrayAdapterDepartment.add("Bureau Of Immigration");
        arrayAdapterDepartment.add("Commercial Tax , Sales Tax , VAT");
        arrayAdapterDepartment.add("Customs,Excise And Service Tax");
        arrayAdapterDepartment.add("Education");
        arrayAdapterDepartment.add("Electricity And Power Supply");
        arrayAdapterDepartment.add("Food And Drug Administration");
        arrayAdapterDepartment.add("Food,Civil Supplies And Consumer Rights");
        arrayAdapterDepartment.add("Foreign Trade");
        arrayAdapterDepartment.add("Forest");
        arrayAdapterDepartment.add("Health And Family Welfare");
        arrayAdapterDepartment.add("Income Tax");
        arrayAdapterDepartment.add("Insurance");
        arrayAdapterDepartment.add("Judiciary");
        arrayAdapterDepartment.add("Labour");
        arrayAdapterDepartment.add("Municipal Services");
        arrayAdapterDepartment.add("Passport");
        arrayAdapterDepartment.add("Pension");
        arrayAdapterDepartment.add("Police");
        arrayAdapterDepartment.add("Post Office");
        arrayAdapterDepartment.add("Public Undertakings");
        arrayAdapterDepartment.add("Public Services");
        arrayAdapterDepartment.add("Public Works Department");
        arrayAdapterDepartment.add("Railways");
        arrayAdapterDepartment.add("Religious Trusts");
        arrayAdapterDepartment.add("Revenue");
        arrayAdapterDepartment.add("Slum Development");
        arrayAdapterDepartment.add("Social Welfare");
        arrayAdapterDepartment.add("Stamps And Registration");
        arrayAdapterDepartment.add("Telecom Services");
        arrayAdapterDepartment.add("Transport");
        arrayAdapterDepartment.add("Urban Development Authorities");
        arrayAdapterDepartment.add("Water Sewage");
        arrayAdapterDepartment.add("Others");

        spinnerDepartment.setAdapter(arrayAdapterDepartment);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storyBean.setDepartment(arrayAdapterDepartment.getItem(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setStates(){


        arrayAdapterStates=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterStates.add("--Select State/Union Territory--");
        arrayAdapterStates.add("Andhra Pradesh");
        arrayAdapterStates.add("Andamon And Nicobar Islands");
        arrayAdapterStates.add("Arunachal Pradesh");
        arrayAdapterStates.add("Assam");
        arrayAdapterStates.add("Bihar");
        arrayAdapterStates.add("Chhattisgarh");
        arrayAdapterStates.add("Chandigarh");
        arrayAdapterStates.add("Dadar And Nagar Haveli");
        arrayAdapterStates.add("Daman And Diu");
        arrayAdapterStates.add("Delhi");
        arrayAdapterStates.add("Goa");
        arrayAdapterStates.add("Gujarat");
        arrayAdapterStates.add("Haryana");
        arrayAdapterStates.add("Himachal Pradesh");
        arrayAdapterStates.add("Jammu And Kashmir");
        arrayAdapterStates.add("Jharkhand");
        arrayAdapterStates.add("Karnataka");
        arrayAdapterStates.add("Kerala");
        arrayAdapterStates.add("Lakshadeep");
        arrayAdapterStates.add("Madhya Pradesh");
        arrayAdapterStates.add("Maharashtra");
        arrayAdapterStates.add("Manipur");
        arrayAdapterStates.add("Meghalaya");
        arrayAdapterStates.add("Mizoram");
        arrayAdapterStates.add("Nagaland");
        arrayAdapterStates.add("Orissa");
        arrayAdapterStates.add("Punjab");
        arrayAdapterStates.add("Puducherry");
        arrayAdapterStates.add("Rajasthan");
        arrayAdapterStates.add("Sikkim");
        arrayAdapterStates.add("TamilNadu");
        arrayAdapterStates.add("Tripura");
        arrayAdapterStates.add("Uttaranchal");
        arrayAdapterStates.add("Uttar Pradesh");
        arrayAdapterStates.add("West Bengal");



        spinnerMainStates.setAdapter(arrayAdapterStates);

        spinnerMainStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                storyBean.setPlace(arrayAdapterStates.getItem(position));

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    void uploadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.INSERT_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success=jsonObject.getInt("success");
                    String message=jsonObject.getString("message");


                    if (success == 1){

                        progressDialog.dismiss();
                        Intent intent = new Intent(AddStoryActivity.this,ImageActivity.class);
                        intent.putExtra("keyStoryBean",storyBean);
                        startActivity(intent);
                        Toast.makeText(getApplication(), "Story Uploaded Success"  + message , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();

                        Toast.makeText(getApplication(), "Story Uploaded Failure"  + message , Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplication(), "Some Volley Error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("storyTitle",storyBean.getStoryTitle());
                map.put("department",storyBean.getDepartment());
                map.put("place",storyBean.getPlace());
                map.put("storyDesc",storyBean.getStoryDesc());
                map.put("category","Corrupt");
                map.put("privacy",privacy);
                map.put("userId",String.valueOf(userId));



                return map;
            }

        };
        requestQueue.add(stringRequest);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId()==R.id.chkAnym) {
            if (isChecked) {
                privacy = "Anonymous";
            } else if (!isChecked)
                privacy = "Not Any";
        }

    }
}
