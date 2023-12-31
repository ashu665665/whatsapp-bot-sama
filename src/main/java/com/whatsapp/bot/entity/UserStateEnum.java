package com.whatsapp.bot.entity;

public enum UserStateEnum {
    INITIAL("INITIAL"),
    OPTIONS("OPTIONS"),
    WAITING_FOR_CASE_NO("WAITING_FOR_CASE_NO"),
    FILE_A_CASE("FILE_A_CASE"),
    END("END"),
    FILE_A_CASE_INITIAL("FILE_A_CASE_INITIAL"),
    FILE_A_CASE_CHOOSE_CATEGORY("FILE_A_CASE_CHOOSE_CATEGORY"),
    FILE_A_CASE_COMPLAINT_INFO("FILE_A_CASE_COMPLAINT_INFO"),
    FILE_A_CASE_ADDRESS_INFO("FILE_A_CASE_ADDRESS_INFO"),
    FILE_A_CASE_OPPOSITION_PARTY_NAME("FILE_A_CASE_OPPOSITION_PARTY_NAME"),
    FILE_A_CASE_OPPOSITION_MAIL_ID("FILE_A_CASE_OPPOSITION_MAIL_ID"),
    FILE_A_CASE_OPPOSITION_PHONE("FILE_A_CASE_OPPOSITION_PHONE"),
    FILE_A_CASE_OPPOSITION_ADDRESS("ILE_A_CASE_OPPOSITION_ADDRESS");

    private final String state;

    UserStateEnum(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
