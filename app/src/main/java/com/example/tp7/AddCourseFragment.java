package com.example.tp7;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tp7.Course;
import com.example.tp7.R;

public class AddCourseFragment extends Fragment {

    private EditText courseName, courseHours;
    private RadioGroup courseType;
    private Spinner teacherSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        courseName = view.findViewById(R.id.courseName);
        courseHours = view.findViewById(R.id.courseHours);
        courseType = view.findViewById(R.id.courseType);
        teacherSpinner = view.findViewById(R.id.teacherSpinner);

        Button addCourseButton = view.findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(v -> addCourse());

        return view;
    }

    private void addCourse() {
        String name = courseName.getText().toString();
        float hours = Float.parseFloat(courseHours.getText().toString());
        String type = ((RadioButton) getView().findViewById(courseType.getCheckedRadioButtonId())).getText().toString();

        int teacherId = (int) teacherSpinner.getSelectedItem(); // Assuming teacher IDs are set in spinner

        if (name.isEmpty() || hours <= 0) {
            Toast.makeText(getActivity(), "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        Course newCourse = new Course(name, hours, type, teacherId);

        // Here you would typically call a method to save the course to the database

        Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();

        // Optionally navigate to ListCoursesFragment after addition
    }
}