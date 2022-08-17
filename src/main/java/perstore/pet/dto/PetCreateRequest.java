package perstore.pet.dto;


import java.util.List;
import lombok.Builder;
import lombok.Data;
import perstore.pet.entity.Category;
import perstore.pet.entity.PetStatus;
import perstore.pet.entity.Tag;

@Data
@Builder
public class PetCreateRequest {

    private int id;

    private String name;

    private Category category;

    private List<String> photoUrls;

    private List<Tag> tags;

    private PetStatus status;
}
