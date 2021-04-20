package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.IssueRequest;

@Repository
public interface IssueRequestRepository extends CrudRepository <IssueRequest, Integer> {
  
}
