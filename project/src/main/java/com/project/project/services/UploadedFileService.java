package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.project.exceptions.FileStorageException;
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

    public void storeFile(MultipartFile file, HolidayRequest request) throws FileStorageException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = request.getRequester().getUsername() + '-' + timestamp.toString();
        try {
            UploadedFile uploadedFile = new UploadedFile(fileName, file.getContentType(), file.getBytes(), request);
            uploadedFileRepository.save(uploadedFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public UploadedFile getFileByRequestId(String requestId) {
        Optional<HolidayRequest> holidayRequestOptional = holidayRequestRepository.findById(requestId);
        return holidayRequestOptional.map(HolidayRequest::getUploadedFile).orElse(null);
    }
} 
