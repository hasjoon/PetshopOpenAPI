package perstore.pet.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter@Setter
public class ApiResponse {

    private int code;
    private HttpStatus status;
    private String message;

}
