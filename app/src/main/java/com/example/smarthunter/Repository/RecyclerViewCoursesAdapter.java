package com.example.smarthunter.Repository;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthunter.Model.Course;
import com.example.smarthunter.R;

public class RecyclerViewCoursesAdapter extends RecyclerView.Adapter<RecyclerViewCoursesAdapter.ViewHolder> {

    int counter = 0;

    private static ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        RecyclerViewCoursesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
        void onEnrollClick(int position, View view);
        boolean onItemLongClick(int position, View view);
    }

    @NonNull
    @Override
    public RecyclerViewCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.recyclerview_courses, parent, false
        );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCoursesAdapter.ViewHolder holder, int position) {
        Course c = CourseRepository.getInstance().getCourses().get(position);
        holder.textViewCourseTitle.setText(c.getCourseTitle());
        //holder.imageViewCourse.setImageURI();
        holder.textViewCourseDescription.setText(c.getCourseDescription());
    }

    @Override
    public int getItemCount() {
        return CourseRepository.getInstance().getCourses().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCourse;
        TextView textViewCourseTitle;
        Button buttonEnroll;
        TextView textViewCourseDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCourse = itemView.findViewById(R.id.imageViewCourse);
            textViewCourseTitle = itemView.findViewById(R.id.textViewCourseTitle);
            buttonEnroll = itemView.findViewById(R.id.buttonEnroll);
            textViewCourseDescription = itemView.findViewById(R.id.textViewCourseDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener == null)
                        return;
                    clickListener.onItemClick(getAdapterPosition(), view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (clickListener == null)
                        return false;

                    return clickListener.onItemLongClick(getAdapterPosition(), view);
                }
            });
            buttonEnroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener == null)
                        return;
                    clickListener.onEnrollClick(getAdapterPosition(), view);
                }
            });
        }

    }
}
