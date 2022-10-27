package com.nds.ssagdaglo.db.repository;

import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
//    @Query("select f from FileEntity f inner join fetch f.user u where u.userEmail = :userEmail", nativeQuery = true)
//    @Query(value = "select f from fileentitiy f inner join fetch f.user u where u.userEmail = :userEmail", nativeQuery = true)
//    @Query(value = "select f from fileentitiy f inner join fetch f.user u where u.userEmail = :userEmail", nativeQuery = true)
    public List<FileEntity> findAllByUserUserEmail(@Param("userEmail") String userEmail);
}
