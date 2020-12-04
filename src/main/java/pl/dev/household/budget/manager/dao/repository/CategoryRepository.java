package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
