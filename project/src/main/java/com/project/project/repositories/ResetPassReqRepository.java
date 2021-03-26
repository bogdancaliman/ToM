package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.ResetPassReq;

@Repository
public interface ResetPassReqRepository extends CrudRepository<ResetPassReq, Integer> {
}