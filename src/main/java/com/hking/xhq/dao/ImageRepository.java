package com.hking.xhq.dao;

import com.hking.xhq.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author：XuHaiqing
 * @Package：com.hking.xhq.dao
 * @Project：Snapchat
 * @name：ImageRepository
 * @Date：2024/11/12 22:42
 * @Filename：ImageRepository
 * @Description:创建一个数据访问接口ImageRepository，用于与数据库交互
 */
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
