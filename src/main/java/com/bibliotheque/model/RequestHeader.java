package com.bibliotheque.model;

import lombok.Getter;

@Getter
public class RequestHeader {
    private Names names;
    private String adminId;
    private String role;
    private String readerId;


    @Getter
    public class Names{
        String firstName;
        String lastName;
    }

}

