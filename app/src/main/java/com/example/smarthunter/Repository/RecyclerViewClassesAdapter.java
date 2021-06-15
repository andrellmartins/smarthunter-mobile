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

import com.example.smarthunter.Model.Class;
import com.example.smarthunter.Model.Course;
import com.example.smarthunter.R;

import java.text.BreakIterator;

public class RecyclerViewClassesAdapter extends RecyclerView.Adapter<RecyclerViewClassesAdapter.ViewHolder>{
    int counter = 0;

    private static ClickListener clickListener;

    public void setClickListener(RecyclerViewClassesAdapter.ClickListener clickListener) {
        RecyclerViewClassesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
        void onEnrollClick(int position, View view);
        boolean onItemLongClick(int position, View view);
    }


    @NonNull
    @Override
    public RecyclerViewClassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.recyclerview_classes, parent, false
        );
        return new RecyclerViewClassesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Class c = CoursesRepository.getInstance().getCourses().get(CoursesRepository.getInstance().getSelectedCourse()).getClasses().get(position);
        holder.textViewClassTitle.setText(c.getClassTitle());
        //holder.imageViewClass.setImageURI();
        holder.textViewClassDescription.setText(c.getClassDescription());
    }

    @Override
    public int getItemCount() {
        return CoursesRepository.getInstance().getCourses().get(CoursesRepository.getInstance().getSelectedCourse()).getClasses().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewClass;
        TextView textViewClassTitle;
        TextView textViewClassDescription;

        public ViewHolder(View view) {
            super(view);
            imageViewClass = itemView.findViewById(R.id.imageViewClass);
            textViewClassTitle = itemView.findViewById(R.id.textViewClassTitle);
            textViewClassDescription = itemView.findViewById(R.id.textViewClassDescription);


        }
    }
}
