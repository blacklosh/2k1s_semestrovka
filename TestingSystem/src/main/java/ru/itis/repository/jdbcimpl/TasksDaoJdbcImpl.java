package ru.itis.repository.jdbcimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.Pack;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repository.TasksRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class TasksDaoJdbcImpl implements TasksRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_SELECT_ALL = "select * from tasks";
    private final static String SQL_SELECT_BY_ID = "select * from tasks where id = ?";

    private final static String SQL_SELECT_PACKS_BY_TASK_USER = "select * from packages where task_id = ? and user_id = ? order by id";
    private final static String SQL_SELECT_PACKS_BY_TASK = "select * from packages where task_id = ? order by id";
    private final static String SQL_SELECT_PACKS_BY_USER = "select * from packages where user_id = ? order by id";
    private final static String SQL_SELECT_PACK_BY_ID = "select * from packages where id = ? order by id";
    private final static String SQL_INSERT_PACK = "insert into packages(task_id, user_id, attempttime, lang, code, result, message, score) values (?,?,?,?,?,?,?,?)";

    private final static String SQL_SELECT_COMPLETED = "select distinct t.* from " +
            "packages p join tasks t on t.id = p.task_id " +
            "where p.user_id = ? and p.score>=100";

    private final static String SQL_SELECT_COMPLETED_ID = "select distinct t.id from " +
            "packages p join tasks t on t.id = p.task_id " +
            "where p.user_id = ? and p.score>=100";

    private final static String SQL_SELECT_STARTED = "select distinct t.* from " +
            "packages p join tasks t on t.id = p.task_id " +
            "where p.user_id = ? and t.id not in ("+SQL_SELECT_COMPLETED_ID+")";

    private final RowMapper<Task> tasksRowMapper = (row, rowNumber) -> Task.builder()
            .id(row.getLong("id"))
            .title(row.getString("title"))
            .description(row.getString("description"))
            .build();

    private final RowMapper<Pack> packsRowMapper = (row, rowNumber) -> Pack.builder()
            .id(row.getLong("id"))
            .taskId(row.getLong("task_id"))
            .userId(row.getLong("user_id"))
            .date(row.getString("attempttime"))
            .lang(row.getString("lang"))
            .code(row.getString("code").replaceAll("<!>", " "))
            .result(row.getString("result"))
            .message(row.getString("message"))
            .score(row.getInt("score"))
            .build();

    public TasksDaoJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Task> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, tasksRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Task> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, tasksRowMapper);
    }

    @Override
    public Task save(Task item) {
        return null;
    }

    @Override
    public void update(Long id, Task item) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Pack> getPacksByTaskUser(Long tid, Long uid) {
        return jdbcTemplate.query(SQL_SELECT_PACKS_BY_TASK_USER, packsRowMapper, tid, uid);
    }

    @Override
    public List<Pack> getPacksByTask(Long tid) {
        return jdbcTemplate.query(SQL_SELECT_PACKS_BY_TASK, packsRowMapper, tid);
    }

    @Override
    public List<Pack> getPacksByUser(Long uid) {
        return jdbcTemplate.query(SQL_SELECT_PACKS_BY_USER, packsRowMapper, uid);
    }

    public Optional<Pack> getPackById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_PACK_BY_ID, packsRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Pack savePack(Pack item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PACK, new String[] {"id"});
            statement.setLong(1, item.getTaskId());
            statement.setLong(2, item.getUserId());
            statement.setString(3, item.getDate());
            statement.setString(4, item.getLang());
            statement.setString(5, item.getCode().replaceAll(" ", "<!>"));
            statement.setString(6, item.getResult());
            statement.setString(7, item.getMessage());
            statement.setInt(8, item.getScore());

            return statement;
        }, keyHolder);
        item.setId(keyHolder.getKey().longValue());
        return item;
    }

    @Override
    public List<Task> findCompletedTasksByUserId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_COMPLETED, tasksRowMapper, id);
    }

    @Override
    public List<Task> findStartedTasksByUserId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_STARTED, tasksRowMapper, id,id);
    }
}
