package perstore.pet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet")
@Getter @Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor

public class Pet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private int id;

    private String name;

    @OneToOne
    private Category category;

    @ElementCollection
    private List<String> photoUrls;

    @JsonIgnore
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PetStatus status;

    private int Is_deleted = 0;


    public static Pet of(int id,String name, Category category, List<Tag> tags, PetStatus status){

        return Pet.builder()
            .id(id)
            .name(name)
            .category(category)
            .tags(tags)
            .status(status)
            .build();
    }
}
