package com.github.hebertcisco.alura.agenda.ui;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.hebertcisco.alura.agenda.R;
import com.github.hebertcisco.alura.agenda.dao.StudentDAO;
import com.github.hebertcisco.alura.agenda.model.Student;

public class StudentFormActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "New Student";
    private EditText fieldName;
    private EditText fieldPhone;
    private EditText fieldEmail;

    private final StudentDAO dao = new StudentDAO(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        setTitle(TITLE_APPBAR);
        initializeFields();
        configureFabSaveStudent();
    }

    private void configureFabSaveStudent() {
        Button saveButton = findViewById(R.id.activity_student_form_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student studentCreated = createStudent();
                save(studentCreated);
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

    @NonNull
    private Student createStudent() {
        String name = fieldName.getText().toString();
        String phone = fieldPhone.getText().toString();
        String email = fieldEmail.getText().toString();

        return new Student(name, phone, email);
    }
}