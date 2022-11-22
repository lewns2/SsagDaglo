package com.nds.ssagdaglo.db.repository;

import com.nds.ssagdaglo.db.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
//    @Query(value = "select f from File f join fetch f.user u where u.email = :userEmail")
    @Query(value = "select f from File f join fetch f.user")
    public Page<File> findAllByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);
    public Optional<File> findAllByFileNo(Long fileNo);

}
