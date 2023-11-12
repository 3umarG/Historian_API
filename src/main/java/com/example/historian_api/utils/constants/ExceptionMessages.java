package com.example.historian_api.utils.constants;

public final class ExceptionMessages {
    public final static String NOT_FOUND_EXCEPTION_MSG = "Not Found Resource !!";
    public final static String NOT_FOUND_PHONE_EXCEPTION_MSG = "There is no account with that phone number , please register first!!";
    public final static String USED_PHONE_NUMBER_EXCEPTION_MSG = "This phone already used with another account!!";
    public final static String USED_PHONE_IN_ANOTHER_DEVICE_EXCEPTION_MSG = "There is an active account in another device with that phone number";
    public final static String NOT_AUTHENTICATED_USER_EXCEPTION_MSG = "Not Authenticated User";
    public static final String ALREADY_SUBSCRIBED_SEMESTER = "لقد قدمت طلب بالاشتراك بالفعل في هذا الترم,الرجاء تحويل المبلغ المطلوب على رقم فودافون كاش ثم التواصل مع الدعم وارفاق صورة من وصل التحويل !!";

    public static String getNotFoundResourceMessage(String resourceName){
        return "There is no %s with that id !!".formatted(resourceName);
    }
}
