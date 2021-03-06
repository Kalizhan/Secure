package com.example.security.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.security.Adapters.StudentsAdapter;
import com.example.security.R;
import com.example.security.Models.StudentModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    EditText et_qr_code;
    Button btn_find;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    StudentsAdapter studentsAdapter;
    ArrayList<StudentModel> modelArrayList;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        et_qr_code = findViewById(R.id.et_qr_code);
        btn_find = findViewById(R.id.btn_find);
        recyclerView = findViewById(R.id.stud_list);

        modelArrayList = new ArrayList<StudentModel>();
        studentsAdapter =new StudentsAdapter(modelArrayList, this);
        recyclerView.setAdapter(studentsAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = et_qr_code.getText().toString();

                firebaseSearchStudents(searchtext, databaseReference);
            }
        });

        showAllTime(databaseReference);
    }

    private void showAllTime(DatabaseReference databaseReference) {
        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelArrayList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    StudentModel studentModel = snap.getValue(StudentModel.class);
                    modelArrayList.add(studentModel);
                }
                studentsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void firebaseSearchStudents(String searchtext, DatabaseReference databaseReference) {
        databaseReference.orderByChild("qr_code")
                .startAt(searchtext).endAt(searchtext + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        modelArrayList.clear();
                        for (DataSnapshot snap : snapshot.getChildren()){
                            StudentModel studentModel = snap.getValue(StudentModel.class);
                            modelArrayList.add(studentModel);
                        }
                        studentsAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }


//    private void firebaseSearchStudents(String searchtext) {
//        Toast.makeText(this, "Started search...", Toast.LENGTH_SHORT).show();
//
//        Query firebaseSearchQuery = studentsdb.orderByChild("qr_code").startAt(searchtext).endAt(searchtext + "\uf8ff");
//
//        studentsAdapter = new FirebaseRecyclerAdapter<StudentModel, StudentsViewHolder>(StudentModel.class, R.layout.students_list, StudentsViewHolder.class, firebaseSearchQuery){
//
//            @NonNull
//            @Override
//            public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull StudentsViewHolder holder, int position, @NonNull StudentModel model) {
//
//            }
//        };
//
////        FirebaseRecyclerOptions<StudentModel> options =
////                new FirebaseRecyclerOptions.Builder<StudentModel>()
////                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Students").orderByChild("qr_code").startAt(searchtext).endAt(searchtext+"\uf8ff"), StudentModel.class)
////                        .build();
//        studentsAdapter=new StudentsAdapter(modelArrayList, this);
//        recyclerView.setAdapter(studentsAdapter);
//    }

    public class StudentsViewHolder extends RecyclerView.ViewHolder{
        TextView name, group, qr_code, phone_number;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            group = itemView.findViewById(R.id.group);
            qr_code = itemView.findViewById(R.id.qr_code);
            phone_number = itemView.findViewById(R.id.phone_number);
        }
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
                intent = new Intent(StudentsActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_guest:
                intent = new Intent(StudentsActivity.this, AddGuestActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_qr_code:
                intent = new Intent(StudentsActivity.this, StudentsActivity.class);
                startActivity(intent);
                break;
        };
        return true;
    }
}