package com.example.hotel_details_project.model.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OptionRequest {
    private Long optionId;
    private Long questionId;
    private String Options;
}
