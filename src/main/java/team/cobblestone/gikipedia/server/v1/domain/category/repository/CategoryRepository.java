package team.cobblestone.gikipedia.server.v1.domain.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team.cobblestone.gikipedia.server.v1.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findByParentIsNull();

    List<Category> findByParent(Category parent);

}
