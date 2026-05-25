package com.lotato.pro.models;

public class Draw {
    private int id;
    private String name;
    private String time;
    private String color;
    private boolean active;

    public Draw() {}

    public Draw(int id, String name, String time, String color, boolean active) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.color = color;
        this.active = active;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
