package com.arczipt.teamup.repo.specifications;

import com.arczipt.teamup.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public interface JobPostingSpecifications {
    static Specification<JobPosting> withTitleLike(String title){
        return new Specification<JobPosting>() {
            @Override
            public Predicate toPredicate(Root<JobPosting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(title.length() == 1)
                    return criteriaBuilder.and();

                return criteriaBuilder.like(root.get("title"), title);
            }
        };
    }

    static Specification<JobPosting> withProjectLike(String project){
        return new Specification<JobPosting>() {
            @Override
            public Predicate toPredicate(Root<JobPosting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(project.length() == 1)
                    return criteriaBuilder.and();

                return criteriaBuilder.like(root.get("project").get("name"), project);
            }
        };
    }

    static Specification<JobPosting> withRoleNameLike(String roleName){
        return new Specification<JobPosting>() {
            @Override
            public Predicate toPredicate(Root<JobPosting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(roleName.length() == 1)
                    return criteriaBuilder.and();

                return criteriaBuilder.like(root.get("role").get("name"), roleName);
            }
        };
    }

    static Specification<JobPosting> withDepartments(ArrayList<String> departments){
        return new Specification<JobPosting>() {
            @Override
            public Predicate toPredicate(Root<JobPosting> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(departments == null || departments.isEmpty())
                    return criteriaBuilder.and();

                Subquery<String> sq = criteriaQuery.subquery(String.class);
                Root<JobPosting> posting = sq.from(JobPosting.class);
                Join<JobPosting, Department> sqDepartments = posting.join("role").join("departments");
                sq.select(posting.get("id")).where(sqDepartments.get("name").in(departments)).groupBy(posting).having(criteriaBuilder.greaterThan(criteriaBuilder.count(sqDepartments), Long.valueOf(0)));
                return root.get("id").in(sq);
            }
        };
    }
}
