package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.HolidayRequest;
import com.project.project.models.Account;
import com.project.project.dtos.RequestStatus;
import java.util.List;

@Repository
public interface HolidayRequestRepository extends CrudRepository<HolidayRequest, Integer> {
    List<HolidayRequest> findAllByRequester_TeamLeaderAndStatus(Account teamLeader, RequestStatus status);
    List<HolidayRequest> findAllByRequesterAndStatus(Account account, RequestStatus status);
}