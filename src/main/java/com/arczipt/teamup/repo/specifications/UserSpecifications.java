package com.arczipt.teamup.repo.specifications;

import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;

public interface UserSpecifications {
    public static Specification<User> withUsernameLike(String username){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("username"), username);
            }
        };
    }

    //https://stackoverflow.com/questions/12061502/spring-data-subquery-within-a-specification

    /**
     * Return user if their username is on the list, which contains those who are members of one or more projects specified in input array.
     *
     * @param projects
     * @return
     */
    public static Specification<User> isMemberOfProject(ArrayList<String> projects){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(projects == null || projects.isEmpty())
                    return criteriaBuilder.and();

                Subquery<String> sq = criteriaQuery.subquery(String.class);
                Root<User> user = sq.from(User.class);
                Join<User, ProjectMember> sqMembers = user.join("projectMember");
                sq.select(user.get("username")).where(sqMembers.get("project").get("name").in(projects)).groupBy(user).having(criteriaBuilder.greaterThan(criteriaBuilder.count(sqMembers), Long.valueOf(0)));
                return root.get("username").in(sq);
            }
        };
    }

    /**
     * Return user if their username is on the list, which contains those who have one or more skills specified in input array.
     *
     * @param skills
     * @return
     */
    public static Specification<User> hasSkill(ArrayList<String> skills){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if(skills == null || skills.isEmpty())
                    return criteriaBuilder.and();

                Subquery<String> sq = criteriaQuery.subquery(String.class);
                Root<User> user = sq.from(User.class);
                Join<User, Skill> sqSkills = user.join("skills");
                sq.select(user.get("username")).where(sqSkills.get("name").in(skills)).groupBy(user).having(criteriaBuilder.greaterThan(criteriaBuilder.count(sqSkills), Long.valueOf(0)));
                return root.get("username").in(sq);
            }
        };
    }
}
