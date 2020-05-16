package edu.step.javafxtabledemo.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author sscerbatiuc
 */
public class EmployeeModel {

    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty age;

    public EmployeeModel(int id, String name, int age) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int ID) {
        this.id.setValue(ID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String nme) {
        this.name.setValue(nme);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.setValue(age);
    }


    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public IntegerProperty ageProperty() {
        return this.age;
    }

    @Override
    public String toString() {
        return "id: " + id.get() + " - " + " name: " + name.get() + " age: " + age.get();
    }

}