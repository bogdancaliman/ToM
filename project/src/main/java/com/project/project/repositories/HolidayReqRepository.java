package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.HolidayReq;
import com.project.project.models.Account;
import java.util.List;

@Repository
public interface HolidayReqRepository extends CrudRepository<HolidayReq, Integer> {
    List<HolidayReq> findAllByAccountReq_Tl(Account tl);
}