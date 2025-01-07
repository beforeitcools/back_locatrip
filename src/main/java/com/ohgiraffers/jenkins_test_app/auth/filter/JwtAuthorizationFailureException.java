package com.ohgiraffers.jenkins_test_app.auth.filter;

public class JwtAuthorizationFailureException extends Exception{

    public JwtAuthorizationFailureException(String message){
        super(message);
    }
}
