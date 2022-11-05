package com.nds.ssagdaglo.api.controller;


import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.api.service.FileService;
import com.nds.ssagdaglo.common.ApiResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    // S3 파일 업로드 - 오디오 파일
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/upload/audio")
    public ApiResponse<?> uploadFile(@RequestPart(value = "key")FileDto.FileResisterReq userNickname, @RequestPart("file") MultipartFile file) throws IOException {
        Boolean isSuccess;
        try {
            isSuccess = fileService.localFileSave(file, userNickname.getUserNickname());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 업로드 중 문제가 발생했습니다.");
        }

        return (isSuccess ? ApiResponse.createSuccessWithNoContent() : ApiResponse.createError("파일 업로드 중 문제가 발생했습니다."));
    }

    // S3 파일 업로드 - 유튜브 링크
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/upload/youtube")
    public ApiResponse<?> uploadLink(@RequestBody List<String> link) throws IOException {
        Boolean isSuccess;
        try {
            isSuccess = fileService.convertAudio(link);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 업로드 중 문제가 발생했습니다.");
        }
        return (isSuccess ? ApiResponse.createSuccessWithNoContent() : ApiResponse.createError("파일 업로드 중 문제가 발생했습니다."));
    }


    // S3 파일 조회
    @GetMapping("/find/{fileNum}")
    public ApiResponse<?> getFile(@PathVariable(name = "fileNum") Long fileNum) throws IOException {
        String res = "false" ;
        try {
            res = fileService.getObject(fileNum);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return (res != "false" ? ApiResponse.createSuccess(res) : ApiResponse.createError("결과 파일 조회 중 에러가 발생했습니다."));
    }

    // 특정 유저의 파일 목록 조회 + 페이징 처리
    @GetMapping("/findAll/{userNickName}")
    public ApiResponse<?> getFileList(@PathVariable String userNickName, @PageableDefault(page=0, size=6, sort = "fileNo", direction = Sort.Direction.DESC) Pageable pagealbe) {
        FileDto.UserFileListRes userFileListRes;

        try {
            userFileListRes = fileService.getUserFileList(userNickName, pagealbe);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 목록 조회 중 문제가 발생했습니다.");
        }
        return ApiResponse.createSuccess(userFileListRes);
    }
}
