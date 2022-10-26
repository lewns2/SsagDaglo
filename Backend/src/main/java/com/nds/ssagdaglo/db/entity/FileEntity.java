package com.nds.ssagdaglo.db.entity;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.dialect.JDataStoreDialect;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@ToString
@Table(name="storage")
@Entity(name="file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="no")
    private Integer fileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;
//    @Column(length = 100, nullable = false)
//    private String email;

    @Column
    private Integer categoryNo;

    @Column(length = 1000)
    private String originPath;

    @Column
    private String filename;

    @Column
    private String outputPath;

    @Column
    private String outputFilename;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    public Integer getFileNo() {
        return this.fileNo;
    }
}
