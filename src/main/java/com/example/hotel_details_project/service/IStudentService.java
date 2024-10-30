package com.example.hotel_details_project.service;

import com.example.hotel_details_project.model.Option;
import com.example.hotel_details_project.model.request.OptionRequest;
import com.example.hotel_details_project.model.request.QuestionRequest;
import com.example.hotel_details_project.model.request.StudentExamRequest;

import java.util.List;

public interface IStudentService {
    Object saveAptitudeExam(StudentExamRequest request);

    Object saveQuestions(QuestionRequest request);

    Object saveOptions(List<OptionRequest> optionRequests);

    List<Option>saveMultipleOptions(List<Option> optionss);
}
