package perstore.pet.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetTagResponse {

    private int id;

    private String name;
}
