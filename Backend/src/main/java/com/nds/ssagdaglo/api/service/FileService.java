package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public Long saveFile(MultipartFile file) throws IOException {
        if(file == null) {
            return null;
        }

        String originName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = originName.substring(originName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir + savedName;

        FileEntity fileEntity = FileEntity.builder()
                .categoryNo(1)
                .originPath(savedPath)
                .filename(originName)
                .build();

        file.transferTo(new java.io.File(savedPath));

        FileEntity savedFileEntity = fileRepository.save(fileEntity);


        return savedFileEntity.getFileNo();
    }

}
