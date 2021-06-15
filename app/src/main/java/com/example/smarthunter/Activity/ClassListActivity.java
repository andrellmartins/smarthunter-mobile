package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.smarthunter.R;
import com.example.smarthunter.Repository.CoursesRepository;
import com.example.smarthunter.Repository.RecyclerViewClassesAdapter;
import com.example.smarthunter.Repository.RecyclerViewCoursesAdapter;

public class ClassListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewCoursesAdapter adapter;
    CoursesRepository coursesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Drawable logo = getResources().getDrawable(R.drawable.logoclasses, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");
        coursesRepository = CoursesRepository.getInstance();
        recyclerView = findViewById(R.id.recyclerViewClasses);
        RecyclerViewClassesAdapter adapter = new RecyclerViewClassesAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),

                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}