package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.project.dtos.PendingIssue;
import com.project.project.models.Account;
import com.project.project.models.IssueRequest;
import com.project.project.repositories.AccountRepository;
import com.project.project.repositories.IssueRequestRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueRequestService {

    private final AccountRepository accountRepository;
    private final IssueRequestRepository issueRequestRepository;

    @Autowired
    public IssueRequestService(AccountRepository accountRepository, IssueRequestRepository issueRequestRepository) {
        this.accountRepository = accountRepository;
        this.issueRequestRepository = issueRequestRepository;
    }

    public void addIssueRequest(Map<String, String> params) {
        Optional<Account> accountOptional = accountRepository.findById(Integer.parseInt(params.get("myId")));
        accountOptional.ifPresent(account -> issueRequestRepository.save(new IssueRequest(params.get("description"), account)));
    }

    public List<PendingIssue> loadAllPendingIssueRequests() {
        Comparator<IssueRequest> compareByIssueReq = Comparator.comparingInt(i -> i.getAccount().getEmployee().getDepartment().getId());
        List<IssueRequest> lst = (List<IssueRequest>) issueRequestRepository.findAll();
        lst.sort(compareByIssueReq);
        return lst.stream().map(s -> new PendingIssue(s.getId(), s.getAccount().getEmployee().getDepartment().getName(), s.getAccount().getEmployee().getName(), s.getDescription())).collect(Collectors.toList());
    }

    public void deleteIssueRequestById(int issueRequestId) {
        issueRequestRepository.deleteById(issueRequestId);
    }

}