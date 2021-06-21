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
import com.example.smarthunter.Repository.CourseRepository;
import com.example.smarthunter.Repository.RecyclerViewCoursesAdapter;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewCoursesAdapter adapter;
    CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Drawable logo = getResources().getDrawable(R.drawable.logocourses, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");


        courseRepository = CourseRepository.getInstance();

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