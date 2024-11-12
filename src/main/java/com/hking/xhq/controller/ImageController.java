package com.hking.xhq.controller;

import com.hking.xhq.dao.ImageRepository;
import com.hking.xhq.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author：XuHaiqing
 * @Package：com.hking.xhq.controller
 * @Project：Snapchat
 * @name：ImageController
 * @Date：2024/11/12 22:43
 * @Filename：ImageController
 * @Description:创建一个控制器ImageController处理图片的上传和查看请求。
 */
@Controller
@RequestMapping("/images")
@Slf4j
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; // 返回上传页面视图
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {

        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + filename);

        try {
            Files.createDirectories(path.getParent());
            file.transferTo(path);

            // 保存文件信息到数据库
            Image image = new Image();
            image.setFilename(filename);
            image.setUploadTime(LocalDateTime.now());
            image.setExpirationTime(LocalDateTime.now().plusMinutes(1)); // 设置过期时间为1分钟
            Image savedImage = imageRepository.save(image);

            // 添加图片链接和ID到页面模型
            model.addAttribute("message", "Image_Uploaded_Successfully.");
            model.addAttribute("imageUrl", "/uploads/" + filename);
            model.addAttribute("imageId", savedImage.getId());
            logger.info("Image uploaded successfully: {}", filename);
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload image: " + e.getMessage());
            logger.error("{}", e.getMessage());
        }

        return "upload"; // 返回上传页面视图
    }

    @PostMapping("/burn/{id}")
    public ResponseEntity<String> burnImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            Path path = Paths.get("src/main/resources/static/uploads/" + image.getFilename());
            try {
                Files.deleteIfExists(path);
                imageRepository.delete(image);
                return ResponseEntity.ok("Image burned successfully");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to burn image");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
    }
}
