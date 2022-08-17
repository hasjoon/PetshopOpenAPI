package perstore.pet.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetTagResponse {

    private int id;

    private String name;
}
