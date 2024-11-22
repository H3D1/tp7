package com.example.tp7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    public List<Teacher> teacherList;
    private final MainActivity mainActivity;
    private homeFragment.OnItemLongClickListener longClickListener;

    public TeacherAdapter(List<Teacher> teacherList, MainActivity mainActivity) {
        this.teacherList = teacherList;
        this.mainActivity = mainActivity;
    }

    public void setOnItemLongClickListener(homeFragment.OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher currentTeacher = teacherList.get(position);
        holder.bind(currentTeacher, position);
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewEmail;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_email);

            // Set up long click listener here
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    return longClickListener.onItemLongClick(getAdapterPosition());
                }
                return false;
            });
        }

        public void bind(Teacher teacher, int position) {
            textViewName.setText(teacher.getNom());
            textViewEmail.setText(teacher.getEmail());
            itemView.setTag(position);  // Store position as tag
        }
    }
}