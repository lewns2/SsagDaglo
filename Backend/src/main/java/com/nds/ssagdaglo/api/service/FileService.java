package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.FileRepository;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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

    // S3 업로드 함수
    public static void uploadObject(MultipartFile file) throws IOException {
        Regions clientRegion = Regions.AP_NORTHEAST_2;
        String originName = file.getOriginalFilename();
        String bucketName = "sdgl-files-bucket";
        String stringObjKeyName = "input_files"; // 경로 + String 전송 시 object 이름
        String fileObjKeyName = "input_files" + originName; // 경로 + 파일 업로드 이름
        String fileName = "C:\\Users\\NDS\\IdeaProjects\\AWS_Test\\src\\main\\java\\uploadTest.txt";

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded Audio Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("plain/text");
//            metadata.addUserMetadata("title", "someTitle");
//            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

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

        // 사용자별 upload 폴더 생성
        String savedPath = System.getProperty("user.dir") + "/upload";
        new File(savedPath).mkdir();

        FileEntity fileEntity = FileEntity.builder()
                .originPath(savedPath)
                .filename(originName)
                .user(userRepository.findByUserNickName(userNickName).get())
                .build();

        // path에 저장
        file.transferTo(new java.io.File(savedPath));

        FileEntity savedFileEntity = fileRepository.save(fileEntity);

        return savedFileEntity.getFileNo();
    }

    // 특정 사용자의 파일 목록을 조회하는 함수
    public FileDto.UserFileListRes getUserFileList(String userNickName, Pageable pageable) {
        List<List> data = new ArrayList<>();

        User user = userRepository.findByUserNickName(userNickName).get();

        Page<FileEntity> fileEntityRes = fileRepository.findAllByUserUserEmail(user.getUserEmail(), pageable);

        List<FileEntity> fileEntityResToList = fileEntityRes.getContent();

        // 전체 페이지 수 넘겨주기
        FileDto.UserFileListRes res = new FileDto.UserFileListRes();
        res.setTotalPages(fileEntityRes.getTotalPages());

        for(int i=0; i<fileEntityResToList.size(); i++) {
            List<String> fileInfo = new ArrayList<>();
            String userFileName = fileEntityResToList.get(i).getFilename();
            Long userFileNum = fileEntityResToList.get(i).getFileNo();
            fileInfo.add(userFileName);
            fileInfo.add(String.valueOf(fileEntityResToList.get(i).getCreatedDate()));
            fileInfo.add(String.valueOf(fileEntityResToList.get(i).getUpdateDate()));
            fileInfo.add(String.valueOf(userFileNum));
            data.add(fileInfo);
        }

        // 파일 정보를 담은 2차원 배열 (파일 이름, 파일 번호, 생성일, 수정일)
        res.setFileInfo(data);

        return res;
    }
}
