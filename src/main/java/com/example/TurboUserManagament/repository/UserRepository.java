package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.appenum.UserRole;
import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumberAndDeletedFalse(PhoneNumber phoneNumber);

    @EntityGraph(attributePaths = {"addresses"})
    Optional<User> findByIdAndDeletedFalse(Long id);

    @Query("""
        select u
        from User u
        left join u.authenticationAccount a
        where (:firstName is null or u.firstName = :firstName)
        and (:lastName is null or u.lastName = :lastName)
        and (:phoneNumber is null or u.phoneNumber = :phoneNumber)
        and a.status = 'ACTIVE'
        and (:role is null or u.role=:role)
        """)
    Page<User> getUsers(@Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("phoneNumber") PhoneNumber phoneNumber,
                        @Param("role") UserRole role,
                        Pageable pageable);

}
