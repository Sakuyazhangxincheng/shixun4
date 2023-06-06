package com.backend.backend.util;

public class Global {

    // HTTP Status Code
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // User Management
    public static final int USER_REGISTER_SUCCESS = 10;
    public static final int USER_REGISTER_FAIL = 11;
    public static final int USER_LOGIN_SUCCESS = 20;
    public static final int USER_LOGIN_FAIL = 21;
    public static final int MAIL_SEND_SUCCESS = 22;
    public static final int VERIFICATION_SUCCESS = 23;
    public static final int VERIFICATION_FAIL = 24;
    public static final int USER_LOGIN_EXIST_ERROR = 25;
    public static final int USER_DELETE_SUCCESS = 26;
    public static final int USER_NOT_FOUND = 27;
    public static final int USER_DELETE_ERROR = 28;
    public static final int USER_INFO_UPDATE_SUCCESS = 30;
    public static final int USER_INFO_UPDATE_FAIL = 31;
    public static final int USER_LOGOUT_SUCCESS = 40;
    public static final int USER_LOGOUT_FAIL = 41;

    // Vehicle Management
    public static final int VEHICLE_ADD_SUCCESS = 50;
    public static final int VEHICLE_ADD_FAIL = 51;
    public static final int VEHICLE_UPDATE_SUCCESS = 60;
    public static final int VEHICLE_UPDATE_FAIL = 61;
    public static final int VEHICLE_DELETE_SUCCESS = 70;
    public static final int VEHICLE_DELETE_FAIL = 71;
    public static final int VEHICLE_STATUS_UPDATE_SUCCESS = 80;
    public static final int VEHICLE_STATUS_UPDATE_FAIL = 81;

    // Order Management
    public static final int VEHICLE_UNLOCK_SUCCESS = 90;
    public static final int VEHICLE_UNLOCK_FAIL = 91;
    public static final int VEHICLE_RETURN_SUCCESS = 100;
    public static final int VEHICLE_RETURN_FAIL = 101;
    public static final int PAYMENT_SUCCESS = 110;
    public static final int PAYMENT_FAIL = 111;

    // Maintenance Management
    public static final int REPORT_ISSUE_SUCCESS = 120;
    public static final int REPORT_ISSUE_FAIL = 121;
    public static final int MAINTENANCE_UPDATE_SUCCESS = 130;
    public static final int MAINTENANCE_UPDATE_FAIL = 131;

    // Data Analysis
    public static final int DATA_COLLECTION_SUCCESS = 140;
    public static final int DATA_COLLECTION_FAIL = 141;
}
