package com.example.petfinder.specification;

import com.example.petfinder.dto.animal.request.AnimalFilter;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.enums.*;
import com.example.petfinder.model.enums.Age;
import org.springframework.data.jpa.domain.Specification;

public class AnimalSpecification {

    public static Specification<Animal> filterBy(AnimalFilter animalFilter) {
        return Specification
                .where(hasType(animalFilter.type()))
                .and(hasSize(animalFilter.size()))
                .and(hasSex(animalFilter.sex()))
                .and(hasSterilization(animalFilter.sterilization()))
                .and(hasAge(animalFilter.age()));
    }

    private static Specification<Animal> hasType(Type type) {
        return ((root, query, criteriaBuilder) -> type == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("type"), type));
    }

    private static Specification<Animal> hasSize(Size size) {
        return (root, query, criteriaBuilder) -> size == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("size"), size);
    }

    private static Specification<Animal> hasSex(Sex sex) {
        return (root, query, criteriaBuilder) -> sex == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("sex"), sex);
    }

    private static Specification<Animal> hasSterilization(Sterilization sterilization) {
        return (root, query, criteriaBuilder) -> sterilization == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("sterilization"), sterilization);
    }

    private static Specification<Animal> hasAge(Age age) {
        return (root, query, criteriaBuilder) -> age == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("age"), age);
    }
}
