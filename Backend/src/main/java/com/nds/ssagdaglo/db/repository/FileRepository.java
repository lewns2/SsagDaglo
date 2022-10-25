package com.nds.ssagdaglo.db.repository;

import com.nds.ssagdaglo.db.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
