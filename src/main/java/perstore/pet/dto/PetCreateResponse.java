package perstore.pet.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import perstore.pet.entity.Category;
import perstore.pet.entity.PetStatus;

@Data
@Builder
public class PetCreateResponse {

    private int id;

    private String name;

    private Category category;

    private List<String> photoUrls;

    private List<PetTagResponse> tags;

    private PetStatus status;
}
