package com.grinder.common.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthResultEnum {
    /**
     * 상태코드를 커스텀으로 구분하여 관리
     */
    //로그인 실패
    LOGIN_FAILED("401", "AUTH_001", "이메일 또는 비밀번호가 일치하지 않습니다."),
    INVALID_SESSION("401", "AUTH_002", "유효하지 않은 토큰입니다."),

    //로그인 실패로 인한 접근제한
    ACCOUNT_LOCKED("403", "AUTH_003", "계정이 잠금 처리되었습니다. 관리자에게 문의하세요."),
    MAX_LOGIN_ATTEMPTS("403", "AUTH_004", "로그인 시도 횟수를 초과했습니다."),
    UNAUTHORIZED_ACCESS("403", "AUTH_005", "접근 권한이 없습니다."),
    //잘못된 요청
    DUPLICATE_EMAIL("409", "AUTH_006", "이미 사용중인 이메일입니다."),
    INVALID_PASSWORD("400", "AUTH_007", "비밀번호는 8~20자리이며 영문, 숫자, 특수문자를 포함해야 합니다."),
    PASSWORD_MISMATCH("400", "AUTH_008", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND("404", "AUTH_009", "회원 정보를 찾을 수 없습니다."),
    INVALID_PARAMETER("400", "AUTH_010", "잘못된 요청 파라미터입니다."),
    INVALID_EMAIL("400", "AUTH_011", "올바르지 않은 이메일 형식입니다."),
    INVALID_NICKNAME("400", "AUTH_012", "닉네임이 올바르지 않습니다."),
    UNSUPPORTED_AUTHENTICATION("400", "AUTH_013", "지원하지 않는 인증 타입입니다."),
    //요청에대한 응답조회 오류
    LOGIN_ATTEMPT_NOT_FOUND("404", "AUTH_014", "로그인 시도 기록을 찾을 수 없습니다."),
    DUPLICATE_NICKNAME("409","AUTH_015","이미 사용 중인 닉네임입니다.");
    private final String status;
    private final String code;
    private final String message;
}
