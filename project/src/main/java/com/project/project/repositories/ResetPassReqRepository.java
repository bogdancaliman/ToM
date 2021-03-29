package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.ResetPassReq;
import com.project.project.models.Account;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface ResetPassReqRepository extends CrudRepository<ResetPassReq, Integer> {
     ResetPassReq findByToken(String token);
    @Transactional
    void deleteAllByAccount(Account account);
    @Transactional
    void deleteAllByExpirationDateIsLessThanEqual(Date expirationDate);
}