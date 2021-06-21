package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smarthunter.Model.DeveloperKey;
import com.example.smarthunter.Repository.UserRepository;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import com.example.smarthunter.R;
import com.example.smarthunter.Repository.CourseRepository;
import com.example.smarthunter.Repository.RecyclerViewClassesAdapter;
import com.example.smarthunter.Repository.RecyclerViewCoursesAdapter;

public class LessonListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewCoursesAdapter adapter;

    UserRepository userRepository;
    CourseRepository courseRepository;

    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        Drawable logo = getResources().getDrawable(R.drawable.logoclasses, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");
        userRepository = (UserRepository) UserRepository.getInstance(LessonListActivity.this,null,null);
        courseRepository = CourseRepository.getInstance(LessonListActivity.this,userRepository.getTokenType(),userRepository.getTOKEN());

        recyclerView = findViewById(R.id.recyclerViewClasses);
        RecyclerViewClassesAdapter adapter = new RecyclerViewClassesAdapter(LessonListActivity.this);
        adapter.setClickListener(new RecyclerViewClassesAdapter.ClickListener() {
            @Override
            public void onVideoClick(int position, View view) {
                String videoLink = courseRepository.getCourses().get(courseRepository.getSelectedCourse()).getLessons().get(position).getLessonLink();
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        LessonListActivity.this, DeveloperKey.DEVELOPER_KEY, videoLink, 0, true, false);
                startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_START_STANDALONE_PLAYER && resultCode != RESULT_OK) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult(data);
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, 0).show();
            } else {
                Toast.makeText(this, "Error Initializing YouTube Player", Toast.LENGTH_LONG).show();
            }
        }
    }
}