package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Category;
import pl.dev.household.budget.manager.dao.repository.CategoryRepository;
import pl.dev.household.budget.manager.domain.CategoryDTO;

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

}
