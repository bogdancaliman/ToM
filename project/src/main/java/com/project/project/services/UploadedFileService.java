package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.project.models.HolidayRequest;
import com.project.project.models.UploadedFile;
import com.project.project.repositories.HolidayRequestRepository;
import com.project.project.repositories.UploadedFileRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UploadedFileService {
    private final UploadedFileRepository uploadedFileRepository;
    private final HolidayRequestRepository holidayRequestRepository;

    @Autowired
    public UploadedFileService(UploadedFileRepository uploadedFileRepository, HolidayRequestRepository holidayRequestRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
        this.holidayRequestRepository = holidayRequestRepository;
    }

    public UploadedFile storeFile(MultipartFile file, HolidayRequest request) throws IOException  {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = request.getRequester().getUsername() + '-' + timestamp.toString();
        UploadedFile uploadedFile = new UploadedFile(fileName, file.getContentType(), file.getBytes(), request);
        return uploadedFileRepository.save(uploadedFile);
    }

    public UploadedFile getFileByRequestId(String requestId) {
        Optional<HolidayRequest> holidayRequestOptional = holidayRequestRepository.findById(requestId);
        return holidayRequestOptional.map(HolidayRequest::getUploadedFile).orElse(null);
    }
} 
