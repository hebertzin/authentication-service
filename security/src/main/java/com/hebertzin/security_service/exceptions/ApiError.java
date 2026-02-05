package com.hebertzin.security_service.exceptions;

public record ApiError(String status, String title,  String details, String instance) { }
