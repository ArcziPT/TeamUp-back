package com.arczipt.teamup.repo;

import com.arczipt.teamup.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserByUsername(String username);

    @Query("from TEAMUP_USER u where u.username like :pattern")
    ArrayList<User> findUsersWithUsernameLike(@Param("pattern") String pattern, Pageable pageable);

    @Query("from TEAMUP_USER u join u.skills where :skillName in (select s.name from u.skills s)")
    ArrayList<User> findUsersBySkillName(@Param("skillName") String skillName, Pageable pageable);
}
