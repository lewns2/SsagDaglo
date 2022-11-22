package com.nds.ssagdaglo.api.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class FileDto {

    // 클라이언트에서 전송한 데이터 파싱을 위한 DTO
    @Getter
    public static class FileResisterReq {
        String userNickname;
    }

    // 유저 파일 목록을 위한 DTO + 총 페이지 수도 함께 보냄
    @Data
    public static class UserFileListRes {
        Integer totalPages;
        List<List> fileInfo;
    }

    // 결과 파일 조회를 위한 DTO (응답 : 대본, 요약, 비디오 링크)
    @Data
    public static class FileResultRes {
        String script;
        String summary;
        String youtubeUrl;
    }


}
