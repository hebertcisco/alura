package com.github.hebertcisco.alura.agenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.hebertcisco.alura.agenda.R;
import com.github.hebertcisco.alura.agenda.dao.StudentDAO;
import com.github.hebertcisco.alura.agenda.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Students";
    private final StudentDAO dao = new StudentDAO(this);
    private ArrayAdapter<Student> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle(TITLE_APPBAR);
        dao.loadFromPreferences();
        configureFabNewStudent();
    }

    private void configureFabNewStudent() {
        FloatingActionButton newStudentButton = findViewById(R.id.activity_student_list_fab_new_student);
        newStudentButton.setOnClickListener(view -> openStudentForm());
    }

    private void openStudentForm() {
        startActivity(new Intent(this, StudentFormActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureList();
    }

    private void configureList() {
        ListView studentList = findViewById(R.id.activity_student_list_listview);
        List<Student> students = dao.all();
        adapter = new ArrayAdapter<Student>(this, 0, students) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
                }

                Student student = getItem(position);

                TextView name = convertView.findViewById(R.id.item_student_name);
                name.setText(student.getName());

                Button deleteButton = convertView.findViewById(R.id.item_student_delete);
                deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog(student));

                convertView.setOnClickListener(view -> openStudentFormWithStudent(student));

                return convertView;
            }
        };
        studentList.setAdapter(adapter);
    }

    private void openStudentFormWithStudent(Student student) {
        Intent intent = new Intent(this, StudentFormActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(Student student) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) -> deleteStudent(student))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteStudent(Student student) {
        dao.delete(student);
        adapter.remove(student);
    }
}