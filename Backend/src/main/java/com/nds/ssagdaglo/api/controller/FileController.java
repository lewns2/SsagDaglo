package com.nds.ssagdaglo.api.controller;


import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.api.service.FileService;
import com.nds.ssagdaglo.common.ApiResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    // S3 파일 업로드
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping("/upload")
    public ApiResponse<?> uploadFile(@RequestPart(value = "key")FileDto.FileResisterReq userNickname, @RequestPart("file") MultipartFile file) throws IOException {
        Boolean isSuccess;
        try {
//            fileService.saveFile(file, userNickname.getUserNickname());
            isSuccess = fileService.uploadObject(file, userNickname.getUserNickname());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 업로드 중 문제가 발생했습니다.");
        }

        return (isSuccess ? ApiResponse.createSuccessWithNoContent() : ApiResponse.createError("파일 업로드 중 문제가 발생했습니다."));
    }

    // S3 파일 조회
    @GetMapping("/list/{fileNum}")
    public ApiResponse<?> getFile(@PathVariable Integer fileNum) throws IOException {
        try {
            fileService.getObject(fileNum);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.createSuccessWithNoContent();
    }

    // 특정 유저의 파일 목록 조회 + 페이징 처리
    @GetMapping("/list/findAll/{userNickName}")
    public ApiResponse<?> getFileList(@PathVariable String userNickName, @PageableDefault(page=0, size=5, sort = "fileNo", direction = Sort.Direction.DESC) Pageable pagealbe) {
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
