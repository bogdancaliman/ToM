package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Employee;
import com.project.project.models.IssueReq;

@Repository
public interface IssueReqRepository extends CrudRepository<IssueReq, Integer> {
}