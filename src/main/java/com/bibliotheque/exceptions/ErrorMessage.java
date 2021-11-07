package com.bibliotheque.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ErrorMessage {
    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String message;
    private String errorClass;
}
