package com.github.hebertcisco.alura.car.model;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Car {
    private final Long id;
    private final String name;
    private final String model;
    private final Long year;

    public Car(
        Long id,
            String name, String model, Long year) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.year = year;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }
    public Long getYear() {
        return year;
    }

    @Nullable
    public Long getId() {
        return id;
    }
}
