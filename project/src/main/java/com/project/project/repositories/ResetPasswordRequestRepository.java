package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.ResetPasswordRequest;
import com.project.project.models.Account;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ResetPasswordRequestRepository extends CrudRepository<ResetPasswordRequest, Integer> {
    Optional<ResetPasswordRequest> findByToken(String token);
    @Transactional
    void deleteAllByAccount(Account account);
    @Transactional
    void deleteAllByExpirationDateIsLessThanEqual(Date expirationDate);
}