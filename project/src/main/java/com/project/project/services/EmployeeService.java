package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.dtos.CalendarEvent;
import com.project.project.dtos.RequestType;
import com.project.project.models.Account;
import com.project.project.models.HolidayRequest;
import com.project.project.dtos.RequestStatus;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.HolidayRequestRepository;
import com.project.project.models.Employee;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeService {

    private final HolidayRequestRepository holidayRequestRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public EmployeeService(HolidayRequestRepository holidayRequestRepository, AccountRepository accountRepository) {
        this.holidayRequestRepository = holidayRequestRepository;
        this.accountRepository = accountRepository;
    }

    public List<Account> loadPossibleDelegates(Account account) {
        List<Account> myTeam = accountRepository.findAllByTeamLeader(account);
        if (myTeam.isEmpty()) return null;
        else {
            List<Account> colleagues;
            if (account.getTeamLeader() == null) {
                colleagues = accountRepository.findAllByTeamLeaderIsNullAndEmployee_Department(account.getEmployee().getDepartment());
                colleagues.remove(account);
                if (colleagues.size() != 0)
                    return colleagues;
                else return myTeam;
            } else {
                colleagues = accountRepository.findAllByTeamLeader(account.getTeamLeader());
                colleagues.remove(account);
                colleagues.add(account.getTeamLeader());
                return colleagues;
            }
        }

    }


    public void addHolidayRequest(Account account, Map<String, String> params) {
        Date start_date;
        Date end_date;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            start_date = format.parse(params.get("start_date"));
            end_date = format.parse(params.get("end_date"));
        } catch (ParseException e) {
            start_date = new Date();
            end_date = new Date();
        }
        Optional<Account> delegateOptional = accountRepository.findById(Integer.parseInt(params.get("delegateId")));
        if (delegateOptional.isPresent()) {
            HolidayRequest newHolidayRequest = new HolidayRequest(
                    RequestType.valueOf(params.get("requestTypeId")),
                    RequestStatus.sentTL,
                    params.get("description"),
                    start_date,
                    end_date,
                    account,
                    delegateOptional.get()
            );
            holidayRequestRepository.save(newHolidayRequest);
        }
        
    }

    public List<CalendarEvent> loadHolidayRequestsOfTeamLeaderForCalendar(Account account) {
        List<HolidayRequest> list = holidayRequestRepository.findAllByRequester_TeamLeaderAndStatus(account, RequestStatus.sentHR);
        list.addAll(holidayRequestRepository.findAllByRequester_TeamLeaderAndStatus(account, RequestStatus.feedHR));
        List<CalendarEvent> result = new ArrayList<>();
        String color = "black";
        for (HolidayRequest h : list) {
            switch (h.getType()) {
                case Fam:
                    color = "#E27D60";
                    break;
                case Hof:
                    color = "#85DCB";
                    break;
                case Med:
                    color = "#E8A87C";
                    break;
                case Rel:
                    color = "#C38D9E";
                    break;

            }
            result.add(new CalendarEvent(h.getId(), h.getRequester().getEmployee().getName(), h.getStart(), h.getEnd(), color));
        }
        return result;
    }

    public List<HolidayRequest> loadPendingHolidayRequestsForATeamLeader(Account account) {
        return holidayRequestRepository.findAllByRequester_TeamLeaderAndStatus(account, RequestStatus.sentTL);
    }

    public void updateStatusOfHolidayRequest(int holidayRequestId, String action) {
        Optional<HolidayRequest> requestOptional = holidayRequestRepository.findById(holidayRequestId);
        if (requestOptional.isPresent()) {
            HolidayRequest request = requestOptional.get();
            if (action.equals("acc"))
                request.setStatus(RequestStatus.sentHR);
            if (action.equals("dec"))
                request.setStatus(RequestStatus.decline);
            holidayRequestRepository.save(request);
        }
    }

}