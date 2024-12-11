package com.erser.board_back.common;

public interface ResponseMessage {
    // 200
    String SUCCESS = "Success.";

    // 400
    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATE_EMAIL = "Duplicate email.";
    String DUPLICATE_NICKNAME = "Duplicate nickname.";
    String DUPLIBCATE_TEL_NUMBER = "Duplicate tel number.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist.";

    // 401
    String SIGN_IN_FAIL = "Login informaion mismatch.";
    String AUTHORIZATION_FAIL = "Authorization failed.";

    // 403
    String NO_PERMISSION = "Do not have permission.";

    // 500
    String DATABASE_ERROR = "Database error.";
    
}
