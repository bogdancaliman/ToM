package com.project.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.IssueReq;

@Repository
public interface IssueReqRepository extends CrudRepository<IssueReq, Integer> {
}