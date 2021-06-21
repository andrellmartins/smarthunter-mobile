package com.example.smarthunter.Repository;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthunter.Model.Lesson;
import com.example.smarthunter.R;

public class RecyclerViewClassesAdapter extends RecyclerView.Adapter<RecyclerViewClassesAdapter.ViewHolder>{
    int counter = 0;

    private static ClickListener clickListener;
    Context context;
    CourseRepository courseRepository;
    public RecyclerViewClassesAdapter(Context context){
        this.context = context;
        courseRepository = CourseRepository.getInstance(context,null,null);
    }

    public void setClickListener(RecyclerViewClassesAdapter.ClickListener clickListener) {
        RecyclerViewClassesAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onVideoClick(int position, View view);
    }


    @NonNull
    @Override
    public RecyclerViewClassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(
                R.layout.recyclerview_lessons, parent, false
        );
        return new RecyclerViewClassesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson c = courseRepository.getCourses().get(courseRepository.getSelectedCourse()).getLessons().get(position);
        holder.textViewClassTitle.setText(c.getLessonTitle());
        holder.textViewClassDescription.setText(c.getLessonDescription());
    }

    @Override
    public int getItemCount() {
        return courseRepository.getCourses().get(courseRepository.getSelectedCourse()).getLessons().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewClass;
        TextView textViewClassTitle;
        TextView textViewClassDescription;

        public ViewHolder(View view) {
            super(view);
            imageViewClass = itemView.findViewById(R.id.imageViewLesson);
            textViewClassTitle = itemView.findViewById(R.id.textViewLessonTitle);
            textViewClassDescription = itemView.findViewById(R.id.textViewLessonDescription);

            imageViewClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener == null)
                        return;
                    clickListener.onVideoClick(getAdapterPosition(), view);
                }
            });

        }
    }
}
