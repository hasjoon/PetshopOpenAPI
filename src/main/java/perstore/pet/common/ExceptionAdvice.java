package perstore.pet.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ApiResponse> NotFountException(PetStoreException e){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(e.getCode());
        apiResponse.setMessage(e.getMessage());
        apiResponse.setStatus(e.getHttpStatus());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
