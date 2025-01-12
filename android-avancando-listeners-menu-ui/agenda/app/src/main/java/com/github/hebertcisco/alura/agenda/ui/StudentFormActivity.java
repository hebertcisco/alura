package com.github.hebertcisco.alura.agenda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.hebertcisco.alura.agenda.R;
import com.github.hebertcisco.alura.agenda.dao.StudentDAO;
import com.github.hebertcisco.alura.agenda.model.Student;

import java.util.UUID;

public class StudentFormActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "New Student";
    private EditText fieldName;
    private EditText fieldPhone;
    private EditText fieldEmail;
    private Student student;

    private final StudentDAO dao = new StudentDAO(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        setTitle(TITLE_APPBAR);
        initializeFields();
        configureFabSaveStudent();

        Intent intent = getIntent();
        if (intent.hasExtra("student")) {
            student = (Student) intent.getSerializableExtra("student");
            assert student != null;
            fillFields(student);
        }
    }

    private void fillFields(Student student) {
        fieldName.setText(student.getName());
        fieldPhone.setText(student.getPhone());
        fieldEmail.setText(student.getEmail());
    }

    private void configureFabSaveStudent() {
        Button saveButton = findViewById(R.id.activity_student_form_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student == null) {
                    student = createStudent();
                    if (dao.existsByEmailAndPhone(student.getEmail(), student.getPhone())) {
                        Toast.makeText(StudentFormActivity.this, "Student with the same email and phone already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        save(student);
                    }
                } else {
                    updateStudent();
                }
            }
        });
    }

    private void initializeFields() {
        fieldName = findViewById(R.id.activity_student_form_name);
        fieldPhone = findViewById(R.id.activity_student_form_phone);
        fieldEmail = findViewById(R.id.activity_student_form_email);
    }

    private void save(Student student) {
        dao.save(student);
        finish();
    }

    private void updateStudent() {
        student.setName(fieldName.getText().toString());
        student.setPhone(fieldPhone.getText().toString());
        student.setEmail(fieldEmail.getText().toString());
        dao.update(student);
        finish();
    }

    @NonNull
    private Student createStudent() {
        String id = UUID.randomUUID().toString();
        String name = fieldName.getText().toString();
        String phone = fieldPhone.getText().toString();
        String email = fieldEmail.getText().toString();

        return new Student(id, name, phone, email);
    }
}