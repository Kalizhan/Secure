package com.example.security.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.security.Models.Model;
import com.example.security.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView fname, wadd, reason, day, time, id;
    EditText temperature;
    Button btnadd, btndel;
    DatabaseReference databaseReference;
    Model model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        fname = findViewById(R.id.fname);
        wadd = findViewById(R.id.wadd);
        reason = findViewById(R.id.reason);
        day = findViewById(R.id.tday);
        time = findViewById(R.id.ttime);
        temperature = findViewById(R.id.temper);
        id =  findViewById(R.id.id);
        btnadd = findViewById(R.id.btnadd);
        btndel = findViewById(R.id.btnadel);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Guests");


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Bundle arguments = getIntent().getExtras();
        String userId = arguments.getString("id");
        String add = arguments.getString("add");
        String fname2 = arguments.getString("fname");
        String day1 = arguments.getString("day");
        String time1 = arguments.getString("time");
        String reason2 = arguments.getString("reason");
        String temperature1 = arguments.getString("temperature");

        id.setText(userId);
        fname.setText(fname2);
        wadd.setText(add);
        reason.setText(reason2);
        day.setText(day1);
        time.setText(time1);
        temperature.setText(temperature1);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Guests").child(day.getText().toString()).child(id.getText().toString());
                String temperatur;
                temperatur = temperature.getText().toString();
                model = new Model(fname2, day1, time1, reason2, add, temperatur, userId);
                databaseReference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProfileActivity.this, "Температурасы енгізіді!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Guests").child(day.getText().toString()).child(id.getText().toString());
                databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProfileActivity.this, "Өшірілді", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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
                intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_guest:
                intent = new Intent(ProfileActivity.this, AddGuestActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_qr_code:
                intent = new Intent(ProfileActivity.this, StudentsActivity.class);
                startActivity(intent);
                break;
        };
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
