package ru.itis.repository;

import ru.itis.models.Pack;
import ru.itis.models.Task;
import ru.itis.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends CrudRepository<Task, Long> {
    public List<Pack> getPacksByTaskUser(Long tid, Long uid);
    public List<Pack> getPacksByTask(Long tid);
    public List<Pack> getPacksByUser(Long uid);
    public Optional<Pack> getPackById(Long id);
    public Pack savePack(Pack pack);

    public List<Task> findCompletedTasksByUserId(Long id);
    public List<Task> findStartedTasksByUserId(Long id);
}
