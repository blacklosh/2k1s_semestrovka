package ru.itis.repository;

import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    public Optional<User> findByNamePassword(String nickname, String password);
    public Optional<User> findByName(String nickname);

    public List<User> findByCompletedTask(Long taskId);
}