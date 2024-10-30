package com.example.hotel_details_project.controller;

import com.example.hotel_details_project.model.Option;
import com.example.hotel_details_project.model.request.OptionRequest;
import com.example.hotel_details_project.model.request.QuestionRequest;
import com.example.hotel_details_project.model.request.StudentExamRequest;
import com.example.hotel_details_project.model.response.CustomResponse;
import com.example.hotel_details_project.model.response.EntityResponse;
import com.example.hotel_details_project.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiAptitude")
public class StudentExamController {

    @Autowired

    private IStudentService iStudentService;

    @PostMapping("/saveAptitudeExam")
    private ResponseEntity<?>saveAptitudeExam(@RequestBody StudentExamRequest request){
        try{
            return new ResponseEntity<>(new EntityResponse(iStudentService.saveAptitudeExam(request),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @PostMapping("/saveQuestions")
    private ResponseEntity<?>saveQuestions(@RequestBody QuestionRequest request){
        try{
            return new ResponseEntity<>(new EntityResponse(iStudentService.saveQuestions(request),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @PostMapping("/saveOptions")
    private ResponseEntity<?>saveOptions(@RequestBody List<OptionRequest> optionRequests){
        try{
            return new ResponseEntity<>(new EntityResponse(iStudentService.saveOptions(optionRequests),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @PostMapping("/saveMultipleOptions")
    private List<Option>saveMultipleOptions(@RequestBody List<Option> optionss) {
        return iStudentService.saveMultipleOptions(optionss);
    }

}
