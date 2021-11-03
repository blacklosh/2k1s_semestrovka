package ru.itis.repository.jdbcimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.repository.UsersRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.itis.models.User;

public class UsersDaoJdbcImpl implements UsersRepository {


    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_SELECT_ALL = "select * from users;";
    private final static String SQL_INSERT = "insert into users (nickname, password, avatarid) VALUES (?, ?, ?);";
    private final static String SQL_SELECT_BY_ID = "select * from users where id = ?;";
    private final static String SQL_SELECT_BY_NAME_PASS = "select * from users where nickname = ? and password = ?";
    private final static String SQL_SELECT_BY_NAME = "select * from users where nickname = ?";
    private final static String SQL_UPDATE_BY_ID = "update users set nickname = ?, password = ?, avatarid = ? where id = ?";
    private static final String SQL_SELECT_BY_COMPLETED_TASK = "select distinct u.* from users u inner join packages p on u.id = p.user_id where p.score>=100 and p.task_id = ?";

    private final RowMapper<User> usersRowMapper = (row, rowNumber) -> User.builder()
            .id(row.getLong("id"))
            .nickname(row.getString("nickname"))
            .password(row.getString("password"))
            .avatarId(row.getLong("avatarid"))
            .build();

    public UsersDaoJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, usersRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByNamePassword(String name, String password) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME_PASS, usersRowMapper, name,password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, usersRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findByCompletedTask(Long taskId) {
        return jdbcTemplate.query(SQL_SELECT_BY_COMPLETED_TASK, usersRowMapper, taskId);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, usersRowMapper);
    }

    @Override
    public User save(User item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});
            statement.setString(1, item.getNickname());
            statement.setString(2, item.getPassword());
            statement.setLong(3, item.getAvatarId());
            return statement;
        }, keyHolder);
        item.setId(keyHolder.getKey().longValue());
        return item;
    }
    
    @Override
    public void update(Long id, User item) {
        jdbcTemplate.update(SQL_UPDATE_BY_ID,
                item.getNickname(),
                item.getPassword(),
                item.getAvatarId(),
                id
                );
    }

    // TODO: - реализовать
    @Override
    public void delete(Long id) {

    }
}
