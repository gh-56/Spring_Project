package com.erser.board_back.common;

public interface ResponseCode {
    // 200
    String SUCCESS = "SU";

    // 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLIBCATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOARD = "NB";

    // 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    // 403
    String NO_PERMISSION = "NP";

    // 500
    String DATABASE_ERROR = "DBE";




    
}