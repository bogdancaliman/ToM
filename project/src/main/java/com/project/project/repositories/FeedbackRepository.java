package com.project.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.project.models.Feedback;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, String> {
}