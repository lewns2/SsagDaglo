package com.nds.ssagdaglo.api.service;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.*;
import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.db.entity.FileEntity;
import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.FileRepository;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;
import java.io.OutputStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import java.io.File;
import java.io.OutputStream;
import java.io.FileInputStream;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.io.IOUtils;
@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    // S3 공통 정보
    Regions clientRegion = Regions.AP_NORTHEAST_2;

    // 유효한 파일 이름으로 변환
    public static String getValidFileName(String fileName) {
        String newFileName = fileName.replaceAll("^[0-9a-zA-Z._-]+", "");
        System.out.println(newFileName);
        if(newFileName.length()==0)
            throw new IllegalStateException(
                    "File Name " + fileName + " results in a empty fileName!");
        return newFileName;
    }



    private void cmdRun(String command) {
        String s;
        Process p;
        try {
            String[] cmd = {"/bin/bash","-c", command};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
        }
    }


    // 사용자별 upload 폴더 생성 + 파일 저장
    public Boolean localFileSave(MultipartFile file, String userNickName) throws IOException {
//        String savedPath = System.getProperty("user.dir") + "\\upload";
//        String savedPath = "/var/app/current";
        String savedPath = "~/upload";
        System.out.println(savedPath);
        System.out.println(savedPath);
        System.out.println(savedPath);
        String originName = file.getOriginalFilename();
        file.transferTo(new java.io.File(savedPath + "/" + originName));
        return uploadObject(file, userNickName, null);
    }

    // 유튜브 링크를 통해 로컬에 파일 저장
    public Boolean convertAudio(List<String> address) throws IOException {
        try {
            URL url = new URL(address.get(0));
            String userNickName = address.get(1);
            String title = getValidFileName(address.get(2));
            String youtubeUrl = address.get(3);
//            String savedPath = "/var/app/current";
            String savedPath = "~/upload";

            cmdRun("mkdir ~/upload; cd ~/upload; wget -N https://raw.githubusercontent.com/Yu-yunjong/uploadStorage/master/youtube.sh; chmod u+x youtube.sh");
            cmdRun("cd ~/upload; bash youtube.sh " + youtubeUrl + " '" + title + ".mp4'");
//            String savedPath = "~/upload";

//            String resource = new ClassPathResource("youtube.sh").getPath();
//            System.out.println(resource);
//            System.out.println(resource);
//            System.out.println(resource);
//            System.out.println(resource);
//             Runtime.getRuntime().exec("/bin/sh -c 'wget https://raw.githubusercontent.com/Yu-yunjong/uploadStorage/master/youtube.sh'");
//             Runtime.getRuntime().exec("/bin/sh -c 'chmod u+x youtube.sh'");
//             Runtime.getRuntime().exec( "/bin/sh -c 'bash youtube.sh " + youtubeUrl + " " + title + ".mp4'");
//            youtubeDL("/bin/sh -c 'wget https://raw.githubusercontent.com/Yu-yunjong/uploadStorage/master/youtube.sh'");
//            youtubeDL("/bin/sh -c 'chmod u+x youtube.sh'");
//            youtubeDL("/bin/sh -c 'bash youtube.sh " + youtubeUrl + " " + title + ".mp4'");

            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(savedPath + "/" + title + ".mp4"); //다운받을 경로 설정
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);  // 처음부터 끝까지 다운로드
            fos.close();

            System.out.println("파일 다운완료");
//            File file = new File(System.getProperty("user.dir") + "\\upload/" + title +".mp4");
            File file = new File(savedPath + "/" + title + ".mp4");
            FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            MultipartFile mFile = new CommonsMultipartFile(fileItem);
            return uploadObject(mFile, userNickName, youtubeUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static void youtubeDL(String cmd) {
        try {
            // Run script
//            Process process = Runtime.getRuntime().exec("echo test!!!!");
            Process process = Runtime.getRuntime().exec(cmd);

            // Read output
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            System.out.println(output.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // S3 업로드 함수
    private Boolean uploadObject(MultipartFile file, String userNickName, String youtubeUrl) throws IOException {
        String bucketName = "sdgl-files-bucket";

//        String localSavedPath = System.getProperty("user.dir") + "\\upload";
//        String localSavedPath = "/var/app/current";
        String localSavedPath = "~/upload";

        String originName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        // S3 업로드 기본 정보
        String fileObjKeyName = userNickName +"/input_files/" + uuid + "_" + originName; // 저장 경로 + 파일 업로드 이름
        String fileName = localSavedPath + "/" + originName;

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            s3Client.putObject(request);

            // 파일 정보 엔티티 저장
            FileEntity fileEntity = FileEntity.builder()
                    .originPath("s3://" + bucketName + "/" + fileObjKeyName) // * S3 url
                    .filename(originName)
                    .uuid(uuid)
                    .user(userRepository.findByUserNickName(userNickName).get())
                    .videoUrl(youtubeUrl)
                    .build();

            fileRepository.save(fileEntity);

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return false;

        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // S3 결과 파일 조회 함수
    public FileDto.FileResultRes getObject(Long fileNum) throws IOException {
        String bucketName = "sdgl-files-bucket-output";
        FileEntity files = fileRepository.findAllByFileNo(fileNum).get();
        String userNickName = files.getUser().getUserNickName();
        String transcribeName = files.getTranscribe_name();
        String videoUrl = files.getVideoUrl();

//        String key = "asd/output_files/e93afd8c-0bc1-4f95-8431-58ccce810dc4_eSIM_clip.mp3.txt";
        String key = userNickName + "/output_files/" + transcribeName + ".txt";
        String key2 = userNickName + "/output_files/" + transcribeName + "_summary" + ".txt";

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        S3Object fullObject2 = null;

        StringBuilder sb = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            System.out.println("Downloading an object");
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            fullObject2 = s3Client.getObject(new GetObjectRequest(bucketName, key2));

            BufferedReader reader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent(), "utf-8"));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(fullObject2.getObjectContent(), "utf-8"));

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String line2 = null;
            while ((line2 = reader2.readLine()) != null) {
                sb2.append(line2).append("\n");
            }


        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
            if (objectPortion != null) {
                objectPortion.close();
            }
            if (headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
        FileDto.FileResultRes res = new FileDto.FileResultRes();
        res.setScript(sb.toString());
        res.setSummary(sb2.toString());
        res.setYoutubeUrl(videoUrl);

        return res;
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
            String status = fileEntityResToList.get(i).getStatus();
            fileInfo.add(userFileName);
            fileInfo.add(String.valueOf(fileEntityResToList.get(i).getCreatedDate()));
            fileInfo.add(String.valueOf(fileEntityResToList.get(i).getUpdateDate()));
            fileInfo.add(String.valueOf(userFileNum));
            fileInfo.add(status);
            data.add(fileInfo);
        }

        // 파일 정보를 담은 2차원 배열 (파일 이름, 파일 번호, 생성일, 수정일, 상태)
        res.setFileInfo(data);

        return res;
    }
}
