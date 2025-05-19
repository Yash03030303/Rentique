package com.capgemini.equipment_rental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.equipment_rental.entity.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    boolean existsByName(String categoryName);
    Categories findByName(String name);

}
