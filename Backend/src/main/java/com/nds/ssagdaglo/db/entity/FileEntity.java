package com.nds.ssagdaglo.db.entity;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="no")
    private Long fileNo;

//    @ManyToOne
//    @JoinColumn(name = "email")
//    @Column
//    private String email;

    @Column
    private Integer categoryNo;

    @Column
    private String originPath;

    @Column
    private String filename;

    @Column
    private String outputPath;

    @Column
    private String outputFilename;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    public Long getFileNo() {
        return this.fileNo;
    }
}
