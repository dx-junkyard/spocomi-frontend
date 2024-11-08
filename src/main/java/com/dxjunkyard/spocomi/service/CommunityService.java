package com.dxjunkyard.spocomi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.io.Files.getFileExtension;

@Service
public class CommunityService {
    private Logger logger = LoggerFactory.getLogger(CommunityService.class);

    @Value("${file.upload-dir}")
    private String upload_dir;

    @Value("${file.image-dir}")
    private String image_dir;

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
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
