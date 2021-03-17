package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.HolidayReq;

@Repository
public interface HolidayReqRepository extends CrudRepository<HolidayReq, Integer> {
}