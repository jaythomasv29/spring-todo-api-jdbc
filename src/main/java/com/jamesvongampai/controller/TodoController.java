package com.jamesvongampai.controller;

import com.jamesvongampai.data.Todo;
import com.jamesvongampai.data.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoDao todoDao;

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable int id) {
        Todo result = todoDao.findById(id);
        System.out.println(result);
        if(result == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoDao.getAll());
    }

    @PostMapping("/")
    public ResponseEntity<Todo> add(@RequestBody Todo todo) {
        return ResponseEntity.ok(todoDao.add(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Todo todo) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if(id != todo.getId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (todoDao.update(todo)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if(todoDao.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
