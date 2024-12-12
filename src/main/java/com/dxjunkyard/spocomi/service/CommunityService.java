package com.dxjunkyard.spocomi.service;

import com.dxjunkyard.spocomi.api.client.CommunityRestClient;
import com.dxjunkyard.spocomi.domain.resource.Community;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.io.Files.getFileExtension;

@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Value("${file.upload-dir}")
    private String upload_dir;

    @Value("${file.image-dir}")
    private String image_dir;

    @Autowired
    private CommunityRestClient communityRestClient;

    private Optional<File> findFileWithId(String myId) {
        File directory = new File(upload_dir);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.startsWith(myId + "."));
            if (files != null && files.length > 0) {
                return Optional.of(files[0]);
            }
        }
        return Optional.empty();
    }

    public Community editCommunity(String token, Community community) {
        Community regiCommunity;
        if (Objects.isNull(community.getId())) {
            regiCommunity = communityRestClient.postCommunityRegistration(token, community);
        } else {
            regiCommunity = communityRestClient.postEditCommunity(token, community);
        }
        // ownerID(before)とcommunityID(after)で画像パスをリネームする
        String newPhotoName = renamePhoto(regiCommunity.getOwnerId(), regiCommunity.getId());
        if (!Objects.equals(newPhotoName, null)) {
            regiCommunity.setProfileImageUrl(newPhotoName);
            communityRestClient.updateCommunity(token, regiCommunity);
        }
        return regiCommunity;
    }

    public String renamePhoto(String myId, Long communityId) {
        try {
            // ディレクトリ内で myId に一致するファイルを探す
            Optional<File> originalFileOptional = findFileWithId(myId);

            if (originalFileOptional.isPresent()) {
                File originalFile = originalFileOptional.get();
                String fileExt = getFileExtension(originalFile.getName());

                String newFileName = communityId + "." + fileExt;
                String newFilePath = upload_dir + "/" + newFileName;
                File renamedFile = new File(newFilePath);

                if (originalFile.renameTo(renamedFile)) {
                    System.out.println("File renamed successfully to " + renamedFile.getName());
                    return image_dir + newFileName;
                } else {
                    System.out.println("Failed to rename file");
                }
            } else {
                System.out.println("No file found with id: " + myId);
            }
            return null;
        } catch (Exception e) {
            logger.debug(e.toString());
            return null;
        }
    }

    public String savePhoto(String myId, MultipartFile photo) {
        try {
            // ファイルを保存する
            String fileName = photo.getOriginalFilename();
            String fileExt = ""; // ファイルの拡張子
            if (fileName != null && fileName.contains(".")) {
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                return ""; // 拡張子がない場合
            }

            // 保存先ディレクトリのパスを定義
            File uploadDir = new File(upload_dir);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 必要ならディレクトリを作成
            }

            // 同じ myId を持つ既存のファイルを検索して削除
            File[] existingFiles = uploadDir.listFiles((dir, name) -> name.startsWith(myId + "."));
            if (existingFiles != null) {
                for (File existingFile : existingFiles) {
                    existingFile.delete();
                }
            }

            // 新しいファイルを保存
            String saveFileName = myId + "." + fileExt;
            String savePath = upload_dir + saveFileName;
            File saveFile = new File(savePath);
            photo.transferTo(saveFile);

            return image_dir + saveFileName;
        } catch (IOException e) {
            logger.info("savePhoto error", e);
            return "";
        }
    }

    public String old_savePhoto(String myId, MultipartFile photo) {
        try {
            // ファイルを保存する
            String fileName = photo.getOriginalFilename();
            String fileExt = ""; // ファイルの拡張子
            if (fileName != null && fileName.contains(".")) {
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            } else {
                return ""; // 拡張子がない場合
            }
            String saveFileName =  myId + "." + fileExt;
            String savePath = upload_dir + saveFileName;
            File saveFile = new File(savePath);
            photo.transferTo(saveFile);
            return image_dir + saveFileName;
        } catch (IOException e) {
            logger.info("savePhoto error");
            return "";
        }
    }

}
