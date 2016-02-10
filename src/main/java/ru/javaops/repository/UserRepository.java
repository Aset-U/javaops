package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.GroupType;
import ru.javaops.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u " +
            " LEFT JOIN u.roles WHERE u.email=:email")
    User findByEmail(@Param("email") String email);

    @Query(" SELECT DISTINCT(ug.user) FROM UserGroup ug " +
            " WHERE ug.group.type=:groupType AND ug.group.project.name=:projectName AND ug.user.active=TRUE")
    List<User> findByProjectAndGroupType(@Param("projectName") String projectName, @Param("groupType") GroupType GroupType);

    @Query(" SELECT DISTINCT(ug.user) FROM UserGroup ug " +
            " WHERE ug.group.name=:groupName AND ug.user.active=TRUE")
    List<User> findByGroupName(@Param("groupName") String groupName);
}