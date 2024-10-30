package com.example.hotel_details_project.service.impl;

import com.example.hotel_details_project.model.Option;
import com.example.hotel_details_project.model.Question;
import com.example.hotel_details_project.model.StudentAptitudeExam;
import com.example.hotel_details_project.model.request.OptionRequest;
import com.example.hotel_details_project.model.request.QuestionRequest;
import com.example.hotel_details_project.model.request.StudentExamRequest;
import com.example.hotel_details_project.repository.OptionRepository;
import com.example.hotel_details_project.repository.QuestionRepository;
import com.example.hotel_details_project.repository.StudentAptitudeExamRepository;
import com.example.hotel_details_project.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentExamService implements IStudentService {

    @Autowired
    private StudentAptitudeExamRepository studentExamRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public Object saveAptitudeExam(StudentExamRequest request) {
        if (studentExamRepository.existsById(request.getAptitudeId())) {
            StudentAptitudeExam studentAptitudeExam = studentExamRepository.findById(request.getAptitudeId()).get();
            studentAptitudeExam.setIsActive(true);
            studentAptitudeExam.setIsDeleted(false);
            studentExamRepository.save(studentAptitudeExam);
            return "updated";
        }
        StudentAptitudeExam studentAptitudeExam = new StudentAptitudeExam();
        studentAptitudeExam.setIsActive(true);
        studentAptitudeExam.setIsDeleted(false);
        studentExamRepository.save(studentAptitudeExam);
        return "save successfully";
    }

    @Override
    public Object saveQuestions(QuestionRequest request) {
        if (questionRepository.existsById(request.getQuestionId())) {
            Question question = questionRepository.findById(request.getQuestionId()).get();
            question.setAptitudeId(request.getAptitudeId());
            question.setQuestions(request.getQuestions());
            question.setIsActive(true);
            question.setIsDeleted(false);
            questionRepository.save(question);
            return "updated";
        }
        Question question = new Question();
        question.setAptitudeId(request.getAptitudeId());
        question.setQuestions(request.getQuestions());
        question.setIsActive(true);
        question.setIsDeleted(false);
        questionRepository.save(question);
        return "save successfully";

    }

    @Override
    public Object saveOptions(List<OptionRequest> optionList)  {

            for (OptionRequest request : optionList) {
                if (optionRepository.existsById(request.getOptionId())) {
                    Option option = optionRepository.findById(request.getOptionId()).get();
                    option.setQuestionId(request.getQuestionId());
                    option.setOptions(request.getOptions());
                    optionRepository.save(option);
                    //return "updated";
                }else {
                    Option option = new Option();

                    option.setQuestionId(request.getQuestionId());
                    option.setOptions(request.getOptions());
                    option.setIsActive(true);
                    option.setIsDeleted(false);
                    optionRepository.save(option);

                   // return "save successfully";
                }

            }
            return "success";
    }




    @Override
    public List<Option> saveMultipleOptions(List<Option> optionss) {
        return optionRepository.saveAll(optionss);

    }
}
