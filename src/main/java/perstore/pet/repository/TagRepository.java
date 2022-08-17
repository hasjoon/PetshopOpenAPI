package perstore.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perstore.pet.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
