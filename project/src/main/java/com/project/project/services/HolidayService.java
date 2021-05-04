package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.project.emails.HolidayStatusUpdateEmail;
import com.project.project.enums.RequestStatus;
import com.project.project.enums.RequestType;
import com.project.project.exceptions.FileStorageException;
import com.project.project.exceptions.NotEnoughDaysException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.models.HolidayRequest;
import com.project.project.models.UploadedFile;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.HolidayRequestRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HolidayService {

    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private final HolidayRequestRepository holidayRequestRepository;
    private final UploadedFileService uploadedFileService;

    @Autowired
    public HolidayService(EmailService emailService, AccountRepository accountRepository, HolidayRequestRepository holidayRequestRepository, UploadedFileService uploadedFileService) {
        this.emailService = emailService;
        this.accountRepository = accountRepository;
        this.holidayRequestRepository = holidayRequestRepository;
        this.uploadedFileService = uploadedFileService;
    }

    public void addHolidayRequest(String username, Map<String, String> params, MultipartFile file) throws FileStorageException, NotEnoughDaysException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            Date start_date;
            Date end_date;
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                start_date = format.parse(params.get("startDate"));
                end_date = format.parse(params.get("endDate"));
            } catch (ParseException e) {
                start_date = new Date();
                end_date = new Date();
            }

            Account delegate;

            if (params.get("delegateId") == null)
                delegate = null;
            else
                delegate = accountRepository.findById(params.get("delegateId")).orElse(null);

            HolidayRequest newHolidayRequest = new HolidayRequest(
                    RequestType.valueOf(params.get("requestTypeId")),
                    RequestStatus.sentTL,
                    params.get("description"),
                    start_date,
                    end_date,
                    accountOptional.get(),
                    delegate
            );
            if (newHolidayRequest.getRequester().getTeamLeader() == null) {
                newHolidayRequest.setStatus(RequestStatus.accTl);
            }

            if (newHolidayRequest.getType() == RequestType.Rel) {
                Account account = newHolidayRequest.getRequester();
                int remainingDays = account.getRemainingDays();
                int workDays = newHolidayRequest.getWorkingDays();
                if (remainingDays - workDays < 0) {
                    throw new NotEnoughDaysException("Sorry! Not enough vacation days");
                }
                account.setRemainingDays(remainingDays - workDays);
                accountRepository.save(account);
            }

            holidayRequestRepository.save(newHolidayRequest);

            if (RequestType.valueOf(params.get("requestTypeId")) == RequestType.Med) {
                try {
                    uploadedFileService.storeFile(file, newHolidayRequest);
                } catch (FileStorageException e) {
                    holidayRequestRepository.delete(newHolidayRequest);
                    throw e;
                }
            }
        }
    }


    private List<HolidayRequest> loadHolidayRequestsBasedOnUserAndStatus(String username, RequestStatus status) throws UserNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.map(account -> holidayRequestRepository.findAllByRequesterAndStatus(account, status)).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " was not found!"));
    }

    public List<HolidayRequest> loadMyAcceptedHolidayRequests(String username) throws UserNotFoundException {
        return loadHolidayRequestsBasedOnUserAndStatus(username, RequestStatus.accTl);
    }

    public List<HolidayRequest> loadMyDeclinedHolidayRequests(String username) throws UserNotFoundException {
        return loadHolidayRequestsBasedOnUserAndStatus(username, RequestStatus.decTL);
    }

    public List<HolidayRequest> loadMyPendingHolidayRequests(String username) throws UserNotFoundException {
        return loadHolidayRequestsBasedOnUserAndStatus(username, RequestStatus.sentTL);
    }
    
    public List<HolidayRequest> loadPendingHolidayRequestsForATeamLeader(String username) throws UserNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        return accountOptional.map(account -> holidayRequestRepository.findAllByRequester_TeamLeaderAndStatus(account, RequestStatus.sentTL)).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " was not found!"));
    }

    public void updateStatusOfHolidayRequest(String holidayRequestId, String action) {
        Optional<HolidayRequest> requestOptional = holidayRequestRepository.findById(holidayRequestId);
        if (requestOptional.isPresent()) {
            String actionEmail = "";
            HolidayRequest request = requestOptional.get();
            if (action.equals("acc")) {
                request.setStatus(RequestStatus.accTl);
                actionEmail = "accepted";
            }

            if (action.equals("dec")) {
                request.setStatus(RequestStatus.decTL);
                Account account = request.getRequester();
                account.setRemainingDays(account.getRemainingDays() + request.getWorkingDays());
                accountRepository.save(account);
                actionEmail = "declined";
            }
            holidayRequestRepository.save(request);
            emailService.sendEmail(new HolidayStatusUpdateEmail(request.getRequester(), actionEmail));
        }
    }

    public UploadedFile getFileOfRequest(String holidayRequestId) {
        return uploadedFileService.getFileByRequestId(holidayRequestId);
    }
}