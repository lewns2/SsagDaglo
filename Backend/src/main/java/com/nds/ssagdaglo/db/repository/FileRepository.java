package com.nds.ssagdaglo.db.repository;

import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
//    @Query(value = "select f from fileentitiy f inner join fetch f.user u where u.userEmail = :userEmail", nativeQuery = true)
    public Page<FileEntity> findAllByUserUserEmail(@Param("userEmail") String userEmail, Pageable pageable);
    public Optional<FileEntity> findAllByFileNo(Long fileNo);

}
