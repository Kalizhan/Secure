package com.example.security.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.security.R;
import com.example.security.Models.StudentModel;
import com.example.security.Activities.StudentsActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.viewHolder> {

    private List<StudentModel> stumodel;
    private StudentsActivity context;

    public StudentsAdapter(List<StudentModel> stumodel, StudentsActivity context){
        this.stumodel = stumodel;
        this.context = context;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name, group, qr_code, phone_number;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            group = itemView.findViewById(R.id.group);
            qr_code = itemView.findViewById(R.id.qr_code);
            phone_number = itemView.findViewById(R.id.phone_number);
        }
    }

    @NonNull
    @Override
    public StudentsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_list, parent, false);
        return new StudentsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAdapter.viewHolder holder, int position) {
        StudentModel item = stumodel.get(position);

        holder.name.setText(item.getName());
        holder.group.setText(item.getGroup());
        holder.qr_code.setText(item.getQr_code());
        holder.phone_number.setText(item.getPhone_number());
    }

    @Override
    public int getItemCount() {
        return stumodel.size();
    }

}
