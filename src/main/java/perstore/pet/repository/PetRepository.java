package perstore.pet.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import perstore.pet.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query("Select p from Pet p where p.status in :status")
    List<Pet> findPetStatus(@Param("status") Enum status);

    @Query("Select p from Pet p where p.tags in :tags")
    List<Pet> findPetTags(@Param("tags") List<String> tags);

}
