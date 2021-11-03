package ru.itis.repository;

import ru.itis.repository.base.CrudRepository;
import ru.itis.models.FileInfo;

public interface FilesRepository extends CrudRepository<FileInfo, Long> {}
