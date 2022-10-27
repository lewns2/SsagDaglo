package com.nds.ssagdaglo.api.dto;

import com.nds.ssagdaglo.db.entity.FileEntity;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Getter
public class FileDto {

    @Getter
    public static class FileResisterReq {
        String userNickname;
    }

    // repository 를 통해 조회한 entity 를 dto 로 변환 용도
//    @Getter
//    public class FileReadRes(List<?> files) {
//        String filename = files;
//    }
}
