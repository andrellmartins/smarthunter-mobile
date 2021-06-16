package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.smarthunter.Model.Class;
import com.example.smarthunter.Model.Course;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.CoursesRepository;
import com.example.smarthunter.Repository.RecyclerViewCoursesAdapter;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewCoursesAdapter adapter;
    CoursesRepository coursesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Drawable logo = getResources().getDrawable(R.drawable.logocourses, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");
        ArrayList<Course> courses = new ArrayList<Course>();
        ArrayList<Class> classes = new ArrayList<Class>();
        //Classes
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));
            classes.add(new Class("WfTqpTDdYL0", "Aula de qualquer culinaria", "ultraMegaDescription"));

        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        courses.add(new Course("qualquer coisa", "Curso basico de java para android", classes, "qualquer coisa 2", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        CoursesRepository.getInstance().setCourses(courses);
        coursesRepository = CoursesRepository.getInstance();
        recyclerView = findViewById(R.id.recyclerViewCourses);
        adapter = new RecyclerViewCoursesAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),

                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.setClickListener(new RecyclerViewCoursesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(CourseListActivity.this, ClassListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onEnrollClick(int position, View view) {

            }

            @Override
            public boolean onItemLongClick(int position, View view) {
                return false;
            }
        });

        }





}