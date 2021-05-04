package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.HolidayRequest;
import com.project.project.models.Account;
import com.project.project.enums.RequestStatus;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface HolidayRequestRepository extends CrudRepository<HolidayRequest, String> {
    List<HolidayRequest> findAllByRequester_TeamLeaderAndStatus(Account teamLeader, RequestStatus status);
    List<HolidayRequest> findAllByRequesterAndStatus(Account account, RequestStatus status);
    List<HolidayRequest> findAllByRequester_Employee_Department_Id(String id);
    @Transactional
    void deleteAllByStartIsLessThan(Date date);
}