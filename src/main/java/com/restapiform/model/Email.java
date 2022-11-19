package com.restapiform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String from;
    private String address;
    private String ccAddress;
    private String title;
    private String content;
    private String template;
}
