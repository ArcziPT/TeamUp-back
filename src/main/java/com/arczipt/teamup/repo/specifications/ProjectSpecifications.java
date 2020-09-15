package com.arczipt.teamup.repo.specifications;

import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public interface ProjectSpecifications {
    static Specification<Project> withNameLike(String name){
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), name);
            }
        };
    }

    static Specification<Project> withMembers(ArrayList<String> members){
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(members == null || members.isEmpty())
                    return criteriaBuilder.and();

                Subquery<String> sq = criteriaQuery.subquery(String.class);
                Root<Project> project = sq.from(Project.class);
                Join<Project, ProjectMember> sqMembers = project.join("members");
                sq.select(project.get("name")).where(sqMembers.get("user").get("username").in(members)).groupBy(project).having(criteriaBuilder.greaterThan(criteriaBuilder.count(sqMembers), Long.valueOf(0)));
                return root.get("name").in(sq);
            }
        };
    }
}
