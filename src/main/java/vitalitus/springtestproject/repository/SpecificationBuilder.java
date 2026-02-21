package vitalitus.springtestproject.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T, P> {
    Specification<T> build(P searchParameter);
}
