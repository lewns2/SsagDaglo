package com.nds.ssagdaglo.api.controller;


import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.api.service.FileService;
import com.nds.ssagdaglo.common.ApiResponse;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<?> uploadFile(@RequestPart(value = "key")FileDto.FileResisterReq userNickname, @RequestPart("file") MultipartFile file) throws IOException {
        System.out.println(userNickname.getUserNickname());

        try {
            fileService.saveFile(file, userNickname.getUserNickname());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 업로드 중 문제가 발생했습니다.");
        }

        return ApiResponse.createSuccessWithNoContent();
    }

    @GetMapping("/list/{userNickName}")
    public ApiResponse<?> getFileList(@PathVariable String userNickName) {
        List<?> resData = new ArrayList<>();

        try {
            resData = fileService.getUserFileList(userNickName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.createError("파일 목록 조회 중 문제가 발생했습니다.");
        }
        return ApiResponse.createSuccess(resData);
    }
}
