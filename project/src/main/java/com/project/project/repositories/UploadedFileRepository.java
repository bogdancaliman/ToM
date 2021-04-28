package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.UploadedFile;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFile, String> {
}
