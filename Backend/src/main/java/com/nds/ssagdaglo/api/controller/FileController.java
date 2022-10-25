package com.nds.ssagdaglo.api.controller;


import com.nds.ssagdaglo.api.service.FileService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
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
    public String uploadFile(@RequestPart(value = "key") String userNickName, @RequestPart("file") MultipartFile file) throws IOException {

        try {
            System.out.print("request NickName : " );
            System.out.println(userNickName);
            System.out.print("request File : ");
            System.out.println(file);
            fileService.saveFile(file);
        }
        catch (Exception E) {
            E.printStackTrace();
        }

        return "에라 모르겠다.";
    }

}
