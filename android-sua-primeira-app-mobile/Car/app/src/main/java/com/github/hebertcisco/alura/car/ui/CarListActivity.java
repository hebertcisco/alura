package com.github.hebertcisco.alura.car.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.hebertcisco.alura.car.R;
import com.github.hebertcisco.alura.car.adapters.configuration.retrofit.CarApiHttpConfiguration;
import com.github.hebertcisco.alura.car.adapters.http.CarHttp;
import com.github.hebertcisco.alura.car.dao.CarDAO;
import com.github.hebertcisco.alura.car.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarListActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Car App";
    private CarDAO dao;
    private ArrayAdapter<Car> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        setTitle(TITLE_APPBAR);
        CarApiHttpConfiguration apiConfig = new CarApiHttpConfiguration(this);
        dao = new CarDAO(this, apiConfig);
        configureFabNewStudent();
    }

    private void configureFabNewStudent() {
        FloatingActionButton newStudentButton = findViewById(R.id.activity_car_list_fab_new_car);
        newStudentButton.setOnClickListener(view -> openStudentForm());
    }

    private void openStudentForm() {
        startActivity(new Intent(this, CarFormActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureList();
    }

    private void configureList() {
        ListView studentList = findViewById(R.id.activity_car_list_listview);
        dao.all(new Callback<List<CarHttp>>() {
            @Override
            public void onResponse(@NonNull Call<List<CarHttp>> call, @NonNull Response<List<CarHttp>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Car> students = response.body().stream()
                            .map(CarHttp::toCar)
                            .collect(Collectors.toList());
                    adapter = new ArrayAdapter<Car>(CarListActivity.this, 0, students) {
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            if (convertView == null) {
                                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_car, parent, false);
                            }

                            Car car = getItem(position);

                            TextView name = convertView.findViewById(R.id.item_student_name);
                            assert car != null;
                            name.setText(car.getName());

                            Button deleteButton = convertView.findViewById(R.id.item_student_delete);
                            deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog(car));

                            return convertView;
                        }
                    };
                    studentList.setAdapter(adapter);
                } else {
                    Log.e("CarListActivity", "Failed to retrieve cars");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarHttp>> call, Throwable t) {
                Log.e("CarListActivity", "Error retrieving cars", t);
                Toast.makeText(CarListActivity.this, "Error retrieving cars", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog(Car car) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this car?")
                .setPositiveButton("Yes", (dialog, which) -> deleteStudent(car))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteStudent(Car car) {
        Long carId = car.getId();
        if (carId != null) {
            dao.delete(carId);
            adapter.remove(car);
        } else {
            Log.e("CarListActivity", "Car ID is null, cannot delete");
        }
    }
}