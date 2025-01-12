package com.github.hebertcisco.alura.agenda.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hebertcisco.alura.agenda.model.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentDAO {
    public Context context;

    public StudentDAO(Context context) {
        this.context = context;
    }

    private final static List<Student> students = new ArrayList<>();
    private static final String PREFS_NAME = "students_prefs";
    private static final String STUDENTS_KEY = "students";

    public void save(Student student) {
        students.add(student);
        saveToPreferences();
    }

    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (Objects.equals(students.get(i).getId(), student.getId())) {
                students.set(i, student);
                saveToPreferences();
                return;
            }
        }
    }

    public List<Student> all() {
        return new ArrayList<>(students);
    }

    public void delete(Student student) {
        students.remove(student);
        saveToPreferences();
    }

    public boolean existsByEmailAndPhone(String email, String phone) {
        for (Student student : students) {
            if (student.getEmail().equals(email) && student.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public void loadFromPreferences() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String studentsJson = prefs.getString(STUDENTS_KEY, null);
        if (studentsJson != null) {
            Type type = new TypeToken<List<Student>>() {}.getType();
            List<Student> loadedStudents = new Gson().fromJson(studentsJson, type);
            students.clear();
            students.addAll(loadedStudents);
        }
    }

    private void saveToPreferences() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String studentsJson = new Gson().toJson(students);
        editor.putString(STUDENTS_KEY, studentsJson);
        editor.apply();
    }
}