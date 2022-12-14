package com.jamesvongampai.data;

public class Todo {
    private int id;
    private String todo;
    private String note;
    private boolean finished;

    public Todo(int id, String todo, String note, boolean finished) {
        this.id = id;
        this.todo = todo;
        this.note = note;
        this.finished = finished;
    }

    public Todo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
