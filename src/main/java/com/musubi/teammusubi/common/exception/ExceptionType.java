package com.musubi.teammusubi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    /**
     * 사용자 예외 처리
     * 가게(S) - 공통(000), 사장(100), 고객(200) - 예외정의순서
     *
     * @since 2024-11-06
     */

    // 공통기능(C)
    USER_NOT_FOUND("C001", "해당 사용자가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_DENIED("C002",
            "비밀번호는 최소 8글자 이상이고, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.",
            HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("C003", "비밀번호와 비밀번호 확인이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_IN_USE("C004", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    EMAIL_MISMATCH("C005", "이메일이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),


    // 가게(S)
    STORE_NOT_FOUND("S001", "선택한 가게가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    OPEN_TIME_AFTER_CLOSE_TIME("S101", "오픈시간이 마감 시간보다 늦을 수 없습니다.", HttpStatus.BAD_REQUEST),
    EXCEEDS_MAXIMUM_STORE_LIMIT("S102", "가게는 최대 3개까지만 운영할 수 없습니다", HttpStatus.BAD_REQUEST),
    NOT_OWNER_OF_STORE("S103", "해당 가게의 주인이 아닙니다.", HttpStatus.UNAUTHORIZED),
    NOT_A_MENU_OF_STORE("S104", "해당 가게의 메뉴가 아닙니다.", HttpStatus.BAD_REQUEST),
    NOT_A_DELIVERY_OF_STORE("S106", "해당 가게의 주문이 아닙니다.", HttpStatus.BAD_REQUEST),
    STORE_NOT_FOUND_OR_STORE_CLOSE("S105", "선택한 가게가 존재하지 않거나, 폐업상태입니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("S201","잘못된 가게 카테고리입니다.", HttpStatus.BAD_REQUEST),

    // 주문(D)
    COMPLETED_DELIVERY_NOT_FOUND("D001", "배달 완료 상태 주문이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MINIMUM_ORDER_AMOUNT("D101", "최소 주문 금액이 0원 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    DELIVERY_NOT_FOUND("D201","주문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_OWNER_OF_DELIVERY("D202","해당 주문의 보유자가 아닙니다.", HttpStatus.UNAUTHORIZED),

    // 메뉴(M)
    MENU_NOT_FOUND("M001", "메뉴가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MENU_NOT_FOR_SALE("M200","판매 중인 메뉴가 아닙니다.", HttpStatus.BAD_REQUEST),

    // 리뷰(R)
    REVIEW_NOT_FOUND("R001", "리뷰가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    REVIEW_ALREADY_REGISTERD("R2001", "이미 리뷰를 작성하였습니다.", HttpStatus.BAD_REQUEST);


    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
