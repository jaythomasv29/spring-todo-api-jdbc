package com.jamesvongampai.data;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Profile("memory")
public class TodoInMemoryDao implements TodoDao {
    private static final List<Todo> todos = new ArrayList<>(Arrays.asList(new Todo(1, "Test note", "A note", false)));


    @Override
    public Todo add(Todo todo) {
        // Find the max id in the todos List
        int nextId = todos.stream()
                .mapToInt(i -> i.getId())
                .max()
                .orElse(0) + 1;
        todo.setId(nextId);
        todos.add(todo);
        return todo;
    }

    @Override
    public List<Todo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public Todo findById(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(Todo todo) {
        int index = 0;
        // loop through todos and continue to increment index until we find a match
        while (index < todos.size() && todos.get(index).getId() != todo.getId()) {
            index++;
        }
        todos.set(index, todo);
        return index < todos.size();
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(i -> i.getId() == id);
    }

}
