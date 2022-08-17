package perstore.pet.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {
    NOT_FOUND(4001, HttpStatus.BAD_REQUEST, "잘못된 값 입니다."),
    INVALID_ID(4002, HttpStatus.BAD_REQUEST, "It is WRONG ID, please check the Id."),
    WRONG_VALIDATION(4003, HttpStatus.BAD_REQUEST, "It is wrong validation."),
    INVALID_INPUT(4004, HttpStatus.BAD_REQUEST, "It is not valid input. Please check the input.");



    private final int code;
    private final HttpStatus httpStatus;
    private final String message;


    ErrorEnum(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;

    }
}
