package com.github.hebertcisco.alura.car.ui;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.hebertcisco.alura.car.R;
import com.github.hebertcisco.alura.car.adapters.configuration.retrofit.CarApiHttpConfiguration;
import com.github.hebertcisco.alura.car.dao.CarDAO;
import com.github.hebertcisco.alura.car.model.Car;

public class CarFormActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "New Car";
    private EditText fieldName;
    private EditText fieldModel;
    private EditText fieldYear;


    private CarDAO dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_form);
        setTitle(TITLE_APPBAR);
        CarApiHttpConfiguration apiConfig = new CarApiHttpConfiguration(this);
        dao = new CarDAO(this, apiConfig);
        initializeFields();
        configureFabSaveStudent();
    }

    private void configureFabSaveStudent() {
        Button saveButton = findViewById(R.id.activity_student_form_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Car studentCreated = createStudent();
                save(studentCreated);
            }
        });
    }

    private void initializeFields() {
        fieldName = findViewById(R.id.activity_car_form_name);
        fieldModel = findViewById(R.id.activity_car_form_model);
        fieldYear = findViewById(R.id.activity_car_form_year);
    }

    private void save(Car car) {
        dao.save(car);
        finish();
    }

    @NonNull
    private Car createStudent() {
        Long id = null;
        String name = fieldName.getText().toString();
        String model = fieldModel.getText().toString();
        Long year = Long.valueOf(fieldYear.getText().toString());

        return new Car(id, name, model, year);
    }
}