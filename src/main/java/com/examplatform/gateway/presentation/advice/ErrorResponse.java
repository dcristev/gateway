package com.examplatform.gateway.presentation.advice;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private List<String> message;
}
