package com.github.hebertcisco.alura.car.adapters.http;

import com.github.hebertcisco.alura.car.model.Car;
import java.io.Serializable;

public class CarHttp implements Serializable {
    private final Long id;
    private final String name;
    private final String model;
    private final Long year;

    public CarHttp(Long id, String name, String model, Long year) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.year = year;
    }

    public Car toCar() {
        return new Car(id, name, model, year);
    }
}