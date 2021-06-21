package com.example.smarthunter.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.smarthunter.Model.Class;
import com.example.smarthunter.Model.Course;
import com.example.smarthunter.R;
import com.example.smarthunter.Repository.CourseRepository;
import com.example.smarthunter.Repository.RecyclerViewCoursesAdapter;
import com.example.smarthunter.Repository.UserRepository;

import java.util.ArrayList;

public class CourseListActivity extends GenericActivity {
    RecyclerView recyclerView;
    RecyclerViewCoursesAdapter adapter;

    UserRepository userRepository;
    CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Drawable logo = getResources().getDrawable(R.drawable.logocourses, getTheme());
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setTitle(" ");
       //repositorys
        userRepository = (UserRepository) UserRepository.getInstance(CourseListActivity.this,null,null);
        CourseRepository.setLoadDataListener(new CourseRepository.LoadDataListener() {
            @Override
            public void onSuccessDataLoad() {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorDataLoad() {
                createToast("Error Loading Courses");
            }
        });
        courseRepository = CourseRepository.getInstance(CourseListActivity.this,userRepository.getTokenType(),userRepository.getTOKEN());
        //adapter
        adapter = new RecyclerViewCoursesAdapter(CourseListActivity.this);
        recyclerView = findViewById(R.id.recyclerViewCourses);
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
                Button b = (Button) view;
                String typeEnroll = (String) b.getText();
                Log.d("typeEnrollUserCourse",typeEnroll);
                if(typeEnroll.toLowerCase().equals("enroll")){
                    CourseRepository.setLoadDataListener(new CourseRepository.LoadDataListener() {
                        @Override
                        public void onSuccessDataLoad() {
                            ArrayList<Integer> coursesIds = userRepository.loggedUser.getCoursesIds();
                            coursesIds.add(courseRepository.getCourses().get(position).getId());
                            userRepository.loggedUser.setCoursesIds(coursesIds);
                            adapter.notifyItemChanged(position);
                            createToast("Enrolled");
                        }

                        @Override
                        public void onErrorDataLoad() {
                            createToast("Could not enroll on course.");
                        }
                    });
                    courseRepository.enrollUser(courseRepository.getCourses().get(position).getId(),userRepository.loggedUser.getId());
                    Log.d("LOGGEDUSERDID",String.valueOf(userRepository.loggedUser.getId()));
                    Log.d("CURRENTCOURSEID",String.valueOf(courseRepository.getCourses().get(position).getId()));
                }else{
                    CourseRepository.setLoadDataListener(new CourseRepository.LoadDataListener() {
                        @Override
                        public void onSuccessDataLoad() {
                            Integer idCourseToRemove = courseRepository.getCourses().get(position).getId();
                            ArrayList<Integer> coursesIds = userRepository.loggedUser.getCoursesIds();
                            for(Integer courseId:coursesIds){
                                if(courseId == idCourseToRemove){
                                    coursesIds.remove((int) courseId);
                                }
                            }
                            userRepository.loggedUser.setCoursesIds(coursesIds);
                            adapter.notifyItemChanged(position);
                            createToast("You Unenrolled of the course");
                        }

                        @Override
                        public void onErrorDataLoad() {
                            createToast("Could not Unenroll");
                        }
                    });
                    courseRepository.unenrollUser(courseRepository.getCourses().get(position).getId());
                    Log.d("CURRENTCOURSEID",String.valueOf(courseRepository.getCourses().get(position).getId()));
                }
            }

            @Override
            public boolean onItemLongClick(int position, View view) {
                return false;
            }
        });

        }

    @Override
    protected void onResume() {
        super.onResume();
        courseRepository.loadData();
    }
}