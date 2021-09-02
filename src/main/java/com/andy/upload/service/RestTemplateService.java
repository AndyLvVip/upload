package com.andy.upload.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2021/9/2 10:37
 */
@Service
public class RestTemplateService {

    public String uploadLargeFile(RestTemplate template, String fileSetInfoId
            , FileSystemResource file) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        body.add("fileSetInfoId", fileSetInfoId);
        String serverUrl = "http://localhost:8080/upload";
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        return template
                .postForObject(serverUrl, requestEntity, String.class);
    }
}
