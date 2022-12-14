package com.jamesvongampai.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("database")
public class TodoDaoImpl implements TodoDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public TodoDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Todo add(Todo todo) {
        final String sql = "INSERT INTO todo(todo, note) VALUES (?, ?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, todo.getTodo());
            stmt.setString(2, todo.getNote());
            return stmt;
        }, keyHolder);
        todo.setId(keyHolder.getKey().intValue());
        return todo;
    }

    @Override
    public List<Todo> getAll() {
        final String sql = "SELECT * FROM todo;";
        return jdbcTemplate.query(sql, new TodoMapper());
    }

    @Override
    public Todo findById(int id) {
        final String sql = "SELECT * FROM todo where id = ?;";
        return jdbcTemplate.queryForObject(sql, new TodoMapper(), id);
    }
    @Override
    public boolean update(Todo todo) {
        final String sql = "UPDATE todo SET " +
                "todo = ?, " +
                "note = ?, " +
                "finished = ? " +
                "WHERE id = ?;";
        return jdbcTemplate.update(sql,
                todo.getTodo(),
                todo.getNote(),
                todo.isFinished(),
                todo.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM todo WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class TodoMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int index) throws SQLException {
            Todo td = new Todo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));
            return td;
        }

    }

}
