package perstore.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perstore.pet.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
