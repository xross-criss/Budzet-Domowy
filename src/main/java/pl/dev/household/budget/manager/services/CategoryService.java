package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Category;
import pl.dev.household.budget.manager.dao.repository.CategoryRepository;
import pl.dev.household.budget.manager.domain.CategoryDTO;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    public CategoryService(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO getCategoryDTOById(Integer categoryId) {
        return modelMapper.map(getCategoryById(categoryId), CategoryDTO.class);
    }

    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public CategoryDTO saveCategory(CategoryDTO dto) {
        return modelMapper.map(categoryRepository.save(modelMapper.map(dto, Category.class)), CategoryDTO.class);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public List<CategoryDTO> getCategoryList(Integer householdId) {
        return modelMapper.map(categoryRepository.findCategoriesByHouseholdId(householdId), new TypeToken<List<CategoryDTO>>() {
        }.getType());
    }

    public void updateCategory(CategoryDTO categoryDTO) throws Exception {
        Category categoryTmp = categoryRepository.findById(categoryDTO.getId()).orElse(null);

        if (categoryTmp == null) {
            throw new Exception("category to be updated not found!");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setHousehold(categoryTmp.getHousehold());

        categoryRepository.save(category);
    }
}
