package com.zup.nosso.banco.digital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ErrorResponseDTO {

    private String error;
    private Integer errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
    private String trace;

}
