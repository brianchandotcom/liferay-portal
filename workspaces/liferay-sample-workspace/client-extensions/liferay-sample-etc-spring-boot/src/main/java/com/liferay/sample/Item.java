package com.liferay.sample;

public class Item {
    private Long id;
    private String name;
    private int age;
    private String title;

    // Default constructor (required for Jackson)
    public Item() {
    }

    public Item(Long id, String name, int age, String title) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.title = title;
    }

    // Getters and setters for the fields (required for Jackson)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
