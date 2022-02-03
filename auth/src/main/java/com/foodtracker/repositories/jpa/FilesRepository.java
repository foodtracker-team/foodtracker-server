package com.foodtracker.repositories.jpa;

import com.foodtracker.models.FileObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends CrudRepository<FileObject, String> {

}
