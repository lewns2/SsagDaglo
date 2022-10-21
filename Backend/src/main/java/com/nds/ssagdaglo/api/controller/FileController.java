package com.nds.ssagdaglo.api.controller;

import com.nds.ssagdaglo.api.request.FileDto;
import com.nds.ssagdaglo.api.service.UserService;
import com.nds.ssagdaglo.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class FileController {

//    @Autowired
//    UserService userService;

    @PostMapping(value = "/upload")
    public String upload(
//            @RequestParam MultipartFile[] uploadfile,
            @RequestPart(value = "file", required = false) MultipartFile file
//            Model model
            ) throws IllegalStateException, IOException
    {
//        List<FileDto> list = new ArrayList<>();
//        for (MultipartFile file : uploadfile) {
//            if (!file.isEmpty()) {
//                // UUID를 이용해서 unique한 파일 이름 생성
//                FileDto dto = new FileDto(UUID.randomUUID().toString(),
//                                        file.getOriginalFilename(),
//                                        file.getContentType());
//                list.add(dto);
//
//                File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());
//
//                // 전달된 내용을 실제 파일로 저장
//                file.transferTo(newFileName);
//            }
//
//        }
        System.out.println(file != null);
//        if (!file.isEmpty()) {
        if (file != null) {
            // UUID를 이용해서 unique한 파일 이름 생성
            FileDto dto = new FileDto(UUID.randomUUID().toString(),
                    file.getOriginalFilename(),
                    file.getContentType());
//            list.add(dto);

            File newFileName = new File(dto.getUuid() + "_" + dto.getFileName());

            // 전달된 내용을 실제 파일로 저장
            file.transferTo(newFileName);
        }



//        model.addAttribute("files", list);
        return "result";
    }

    @Value("${spring.servlet.multipart.location}")
    String filePath;

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@ModelAttribute FileDto dto) throws IOException {
        Path path = Paths.get(filePath + "/" + dto.getUuid() + "_" + dto.getFileName());
        String contentType = Files.probeContentType(path);

        // header를 통해서 다운로드 되는 파일 정보 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename(dto.getFileName(), StandardCharsets.UTF_8)
                                                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        org.springframework.core.io.Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }
}
