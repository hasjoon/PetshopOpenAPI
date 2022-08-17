package perstore.pet.common;

import lombok.Getter;

@Getter
public class NotFoundException extends PetStoreException {

    private static ErrorEnum errorEnum = ErrorEnum.NOT_FOUND;

    public NotFoundException() {
        super(errorEnum.getCode(), errorEnum.getMessage(), errorEnum.getHttpStatus());
    }
}
