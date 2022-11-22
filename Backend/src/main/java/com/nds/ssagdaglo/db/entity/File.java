package com.nds.ssagdaglo.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
//@Table(name="storage")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
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
