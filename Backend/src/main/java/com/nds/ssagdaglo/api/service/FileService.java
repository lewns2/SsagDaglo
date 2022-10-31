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

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

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

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

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
//                .createdDate(LocalDate.now())
//                .updateDate(LocalDate.now())
                .build();

        file.transferTo(new java.io.File(savedPath));

        // S3에 업로드
//        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
//        final PutObjectRequest request = new PutObjectRequest(bucket, savedName, file);
        // 업로드 시도
//        final Upload upload = transferManager.upload(request);

        //
        FileEntity savedFileEntity = fileRepository.save(fileEntity);

        return savedFileEntity.getFileNo();
    }

    // 사용자의 파일 목록을 조회하는 함수
    public List<List> getUserFileList(String userNickName) {
        List<List> data = new ArrayList<>();

        User user = userRepository.findByUserNickName(userNickName).get();

        List<FileEntity> fileEntityRes = fileRepository.findAllByUserUserEmail(user.getUserEmail());

        for(int i=0; i<fileEntityRes.size(); i++) {
            List<String> fileInfos = new ArrayList<>();
            String userFileName = fileEntityRes.get(i).getFilename();
            Long userFileNum = fileEntityRes.get(i).getFileNo();
            fileInfos.add(userFileName);
            fileInfos.add(String.valueOf(fileEntityRes.get(i).getCreatedDate()));
            fileInfos.add(String.valueOf(fileEntityRes.get(i).getUpdateDate()));
            fileInfos.add(String.valueOf(userFileNum));
            data.add(fileInfos);
        }

        return data;
    }
}
