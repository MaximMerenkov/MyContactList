package com.example.mycontactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class ContactSettingsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_setting);

        initListButton();
        initMapButton();
        initSettingsButton();

        initSortByClick();
        initSortOrderClick();
        initSetColor();
        initSettings();




    }

    private void initListButton() {
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactSettingsActivity.this, ContactListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMapButton() {
        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ContactSettingsActivity.this, ContactMapActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);


    }
    private void initSettings(){
        String sortBy = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortfield", "contactname");
        String sortOrder = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");
        String setColor = getSharedPreferences("MyContactListPreferences",
                Context.MODE_PRIVATE).getString("setcolor","gold");
        RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
        RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
        RadioButton rbBirthday = (RadioButton) findViewById(R.id.radioBirthday);
        if(sortBy.equalsIgnoreCase("contactname")){
            rbName.setChecked(true);
        }
        else if(sortBy.equalsIgnoreCase("city")){
            rbCity.setChecked(true);
        }
        else{
            rbBirthday.setChecked(true);
        }
        RadioButton rbAscending = (RadioButton) findViewById(R.id.radioAscending);
        RadioButton rbDescending = (RadioButton) findViewById(R.id.radioDescending);
        if(sortOrder.equalsIgnoreCase("ASC")){
            rbAscending.setChecked(true);
        }
        else{
            rbDescending.setChecked(true);
        }
        RadioButton gold = (RadioButton)findViewById(R.id.radioGold);
        RadioButton gray = (RadioButton)findViewById(R.id.radioGray);
        RadioButton blue = (RadioButton)findViewById(R.id.radioBlue);
        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_contact_setting);
        if(setColor.equalsIgnoreCase("gold")){
            gold.setChecked(true);
            layout.setBackgroundColor(getResources().getColor(R.color.pale_goldenrod));
        }
        else if(setColor.equalsIgnoreCase("gray")){
            gray.setChecked(true);
            layout.setBackgroundColor(getResources().getColor(R.color.slate_gray));
        }
        else{
            blue.setChecked(true);
            layout.setBackgroundColor(getResources().getColor(R.color.medium_slate_blue));
        }

    }
    private void initSortByClick(){
        RadioGroup rgSortBy = (RadioGroup) findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbName = (RadioButton) findViewById(R.id.radioName);
                RadioButton rbCity = (RadioButton) findViewById(R.id.radioCity);
                if(rbName.isChecked()){
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield","contactname").commit();
                }
                else if(rbCity.isChecked()){
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield","city").commit();
                }
                else{
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").commit();
                }

            }
        });

    }
    private void initSortOrderClick(){
        RadioGroup rgSortOrder = (RadioGroup)findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = (RadioButton)findViewById(R.id.radioAscending);
                if(rbAscending.isChecked()){
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();
                }
                else{
                    getSharedPreferences("MyContactListPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder","DESC").commit();
                }

            }
        });
    }

    private void initSetColor(){
        RadioGroup rgBackgroundColor = (RadioGroup)findViewById(R.id.radioGroupBackgroundColor);
        rgBackgroundColor.setOnCheckedChangeListener((arg0, arg1)->{
            RadioButton gold = (RadioButton)findViewById(R.id.radioGold);
            RadioButton gray = (RadioButton)findViewById(R.id.radioGray);
            RadioButton blue = (RadioButton)findViewById(R.id.radioBlue);
            final RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_contact_setting);
            if(gold.isChecked()){
                getSharedPreferences("MyContactListPreferences",
                        Context.MODE_PRIVATE).edit().putString("setcolor","gold").commit();
                layout.setBackgroundColor(getResources().getColor(R.color.pale_goldenrod));

            }
            else if(gray.isChecked()){
                getSharedPreferences("MyContactListPreferences",
                        Context.MODE_PRIVATE).edit().putString("setcolor","gray").commit();
                layout.setBackgroundColor(getResources().getColor(R.color.slate_gray));
            }
            else{
                getSharedPreferences("MyContactListPreferences",
                        Context.MODE_PRIVATE).edit().putString("setcolor","blue").commit();
               layout.setBackgroundColor(getResources().getColor(R.color.medium_slate_blue));
            }

        });


    }
}

