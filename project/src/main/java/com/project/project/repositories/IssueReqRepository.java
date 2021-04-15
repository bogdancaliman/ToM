package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Employee;
import com.project.project.models.IssueReq;

import java.util.List;

@Repository
public interface IssueReqRepository extends CrudRepository <IssueReq, Integer> {
  List<IssueReq> findAll();
}