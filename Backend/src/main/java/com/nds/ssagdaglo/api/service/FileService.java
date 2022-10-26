package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.repository.FileRepository;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    private final UserRepository userRepository;
    // 닉네임을 통해 유저 이메일 찾아 저장하기

    @Transactional(rollbackFor = Exception.class)
    public Integer saveFile(MultipartFile file, String userNickName) throws IOException {
        if(file == null) {
            return null;
        }

        String originName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = originName.substring(originName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir + savedName;



        // 생성 날짜, 수정 날짜
//        LocalDate localCreateDate = LocalDate.now();
//        LocalDate localUpdateDate = LocalDate.now();

        FileEntity fileEntity = FileEntity.builder()
                .originPath(savedPath)
                .filename(originName)
                .user(userRepository.findByUserNickName(userNickName).get())
                .createdDate(LocalDate.now().atStartOfDay())
                .updateDate(LocalDate.now().atStartOfDay())
                .build();

        file.transferTo(new java.io.File(savedPath));

        FileEntity savedFileEntity = fileRepository.save(fileEntity);


        return savedFileEntity.getFileNo();
    }

}
