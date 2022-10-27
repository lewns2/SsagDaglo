package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.FileRepository;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    // 업로드된 파일을 저장하는 함수
    @Transactional(rollbackFor = Exception.class)
    public Long saveFile(MultipartFile file, String userNickName) throws IOException {
        if(file == null) {
            return null;
        }

        String originName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = originName.substring(originName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir + savedName;

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

    // 사용자의 파일 목록을 조회하는 함수
    public List<String> getUserFileList(String userNickName) {
        List<String> data = new ArrayList<>();

        User user = userRepository.findByUserNickName(userNickName).get();

        List<FileEntity> fileEntityRes = fileRepository.findAllByUserUserEmail(user.getUserEmail());

        for(int i=0; i<fileEntityRes.size(); i++) {
            String userFileName = fileEntityRes.get(i).getFilename();
            data.add(userFileName);
        }

        return data;
    }
}
