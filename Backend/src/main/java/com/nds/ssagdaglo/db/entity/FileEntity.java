package com.nds.ssagdaglo.db.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.dialect.JDataStoreDialect;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name="storage")
@Entity(name="file")
@EntityListeners(AuditingEntityListener.class)
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="no")
    private Long fileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

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

    @Column
    private String uuid;

    @Column
    private String transcribe_name;

    @Column
    private String status;

    @Column
    private String videoUrl;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate updateDate;

    public Long getFileNo() {
        return this.fileNo;
    }
}
