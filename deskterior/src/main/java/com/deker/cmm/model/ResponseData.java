package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseData {
    private String responseCode;
    private String message;
    private List<?> data;
}
