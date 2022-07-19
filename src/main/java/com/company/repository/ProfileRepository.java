package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer> {
    boolean existsByEmail(String email);

    Optional<ProfileEntity> findByEmail(String username);

    boolean existsByIdAndStatus(Integer id, ProfileStatus status);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status=:status where id=:id")
    void updateStatus(@Param("status") ProfileStatus status,
                      @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set password=:password where email=:email")
    void updatePassword(@Param("password") String password,
                        @Param("email") String email);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set username=:username where email=:email")
    void updateUsername(@Param("username") String username,
                        @Param("email") String email);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set email=:email,status=:status where email=:username")
    void updateEmailAndStatus(@Param("email") String email,
                              @Param("status") ProfileStatus status,
                              @Param("username") String username);

    boolean existsByPhotoId(String id);
}
