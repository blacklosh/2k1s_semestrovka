package ru.itis.repository.jdbcimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.models.FileInfo;
import ru.itis.repository.FilesRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class FilesRepositoryImpl implements FilesRepository {

    private final static String SQL_INSERT = "insert into file_info(storage_file_name, original_file_name, type, size) " +
            "values (?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update file_info set storage_file_name = ?, original_file_name = ?, type = ?, size = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from file_info where id = ?";

    private JdbcTemplate jdbcTemplate;

    public FilesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<FileInfo> fileRowMapper = (row, rowNumber) ->
        FileInfo.builder()
                .id(row.getLong("id"))
                .originalFileName(row.getString("original_file_name"))
                .storageFileName(row.getString("storage_file_name"))
                .size(row.getLong("size"))
                .type(row.getString("type"))
                .build();


    @Override
    public FileInfo save(FileInfo entity) {
        if(entity.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, entity.getStorageFileName());
                statement.setString(2, entity.getOriginalFileName());
                statement.setString(3, entity.getType());
                statement.setLong(4, entity.getSize());
                return statement;
            }, keyHolder);
            entity.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    entity.getStorageFileName(),
                    entity.getOriginalFileName(),
                    entity.getType(),
                    entity.getSize(),
                    entity.getId()
            );
        }
        return entity;
    }

    @Override
    public void update(Long id, FileInfo item) {
        item.setId(id);
        save(item); // Save реализует и обновление по id
    }

    @Override
    public Optional<FileInfo> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, fileRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // TODO: Реализовать
    @Override
    public List<FileInfo> findAll() {
        return null;
    }

    // TODO: Реализовать
    @Override
    public void delete(Long id) {}
}