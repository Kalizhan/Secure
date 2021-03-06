package com.example.security.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.security.Models.Model;
import com.example.security.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddGuestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Spinner spinner1, spinner2;
    Button button;
//    EditText date_time_in;
    EditText et_name, date_in, time_in;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        et_name = findViewById(R.id.et_name);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        button = findViewById(R.id.btn);
        date_in=findViewById(R.id.date_input);
        time_in=findViewById(R.id.time_input);
//        temp = findViewById(R.id.temperature);
//        date_time_in=findViewById(R.id.date_time_input);
        databaseReference = FirebaseDatabase.getInstance().getReference();





        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.spinner_list1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.spinner_list2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        
//        date_time_in.setInputType(InputType.TYPE_NULL);
        date_in.setInputType(InputType.TYPE_NULL);
        time_in.setInputType(InputType.TYPE_NULL);

        date_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_in);
            }
        });

        time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(time_in);
            }
        });

//        date_time_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateTimeDialog(date_time_in);
//            }
//        });
    }

//    private void showDateTimeDialog(EditText date_time_in) {
//        final Calendar calendar=Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                calendar.set(Calendar.YEAR,year);
//                calendar.set(Calendar.MONTH,month);
//                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//
//                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        calendar.set(Calendar.MINUTE,minute);
//
//                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");
//
//                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
//                    }
//                };
//
//                new TimePickerDialog(AddGuestActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
//            }
//        };
//
//        new DatePickerDialog(AddGuestActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }

    private Boolean validateUsername(){
        String fname = et_name.getText().toString();

        if(fname.isEmpty()){
            et_name.setError("Атыңыз бос болмауы керек");
            return false;
        }else{
            return true;
        }
    }

    private boolean validateDay(){
        String day = date_in.getText().toString();

        if (day.isEmpty()){
            date_in.setError("Күн бос болмауы керек");
            return false;
        }else{
            return true;
        }
    }

    private boolean validateTime(){
        String time = time_in.getText().toString();

        if (time.isEmpty()){
            time_in.setError("Уақыт бос болмауы керек");
            return false;
        }else{
            return true;
        }
    }

    public void check(View v){
        if(!validateUsername() | !validateDay() | !validateTime()){
            return;
        }else{
                    String userId = databaseReference.push().getKey();
                    String userFname = et_name.getText().toString();
                    String userDay = date_in.getText().toString();
                    String usertime = time_in.getText().toString();
                    String userReason = "Себебі: " + spinner1.getSelectedItem().toString();
                    String userWhoAdd = "Кім енгізді: " + spinner2.getSelectedItem().toString();
                    String userTemp = " градус";
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Guests").push();
                    Model model = new Model(userFname, userDay, usertime, userReason, userWhoAdd, userTemp, userId);

//                    String mkey = databaseReference.child("Guests/").push().getKey();
                    databaseReference.child("Guests").child(date_in.getText().toString()).child(userId).setValue(model);

                    Toast.makeText(AddGuestActivity.this, "Енгізілді!", Toast.LENGTH_SHORT).show();

//                Map<String, Object> map = new HashMap<>();
//                map.put("Full name", et_name.getText().toString());
//                map.put("Time", date_time_in.getText().toString());
//                map.put("Reason", spinner1.getSelectedItem().toString());
//                map.put("Who Add", spinner2.getSelectedItem().toString());
//
//                databaseReference.setValue(map);
                }
            }

    private void showTimeDialog(EditText time_in) {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(AddGuestActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
    }

    private void showDateDialog(EditText date_in) {
        final Calendar calendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(AddGuestActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_list:
                intent = new Intent(AddGuestActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_guest:
                intent = new Intent(AddGuestActivity.this, AddGuestActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_qr_code:
                intent = new Intent(AddGuestActivity.this, StudentsActivity.class);
                startActivity(intent);
                break;
        };
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}