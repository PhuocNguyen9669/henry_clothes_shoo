package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

//    @Query("SELECT new com.cg.model.dto.CategoryDTO (" +
//            "c.id, " +
//            "c.title " +
//            ") " +
//            "FROM Category AS c")
//    List<CategoryDTO> findAllCategoryDTO();
}
