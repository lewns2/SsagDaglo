package com.nds.ssagdaglo.api.controller;


import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.api.service.FileService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "key")FileDto.FileResisterReq userNickname, @RequestPart("file") MultipartFile file) throws IOException {
        System.out.println(userNickname.getUserNickname());

        try {
            fileService.saveFile(file, userNickname.getUserNickname());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "에라 모르겠다.";
    }

//    @ControllerAdvice
//    public ResponseEntity<?> responseException() {
//        return ResponseEntity.notFound().;
//    }

}
