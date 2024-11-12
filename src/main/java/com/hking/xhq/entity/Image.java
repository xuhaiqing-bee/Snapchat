package com.hking.xhq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author：XuHaiqing
 * @Package：com.hking.xhq.entity
 * @Project：Snapchat
 * @name：Image
 * @Date：2024/11/12 22:37
 * @Filename：Image
 * @Description:创建一个Image实体类，表示上传的图片信息。包括文件名、上传时间、过期时间等字段。
 */
@Entity
@Getter
@Setter
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 图片ID
    @Column(name = "filename")
    private String filename; // 文件名
    @Column(name = "upload_time")
    private LocalDateTime uploadTime; // 上传时间
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime; // 过期时间
}
