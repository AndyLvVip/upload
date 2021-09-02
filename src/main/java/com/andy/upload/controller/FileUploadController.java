package com.andy.upload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2021/9/2 15:57
 */
@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public void upload(String fileSetInfoId
            , MultipartFile file) {
        System.out.println("--------------------------" + fileSetInfoId);
        System.out.println("--------------------------" + file.getOriginalFilename());
    }
}
