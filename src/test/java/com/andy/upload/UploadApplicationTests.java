package com.andy.upload;

import com.andy.upload.service.RestTemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UploadApplicationTests {

	@Autowired
	RestTemplateService restTemplateService;

	String filePath = System.getProperty("user.dir") + "/largefile";

	@Test
	public void restTemplateUpload() {
		createLargeFile();
		File file = new File(filePath);
		assertTrue(file.exists());
		restTemplateService.uploadLargeFile(template(),
				UUID.randomUUID().toString()
				, new FileSystemResource(file)
		);
	}

	@Test
	public void restTemplateUploadWithInterceptor() {
		createLargeFile();
		File file = new File(filePath);
		assertTrue(file.exists());
		restTemplateService.uploadLargeFile(templateWithInterceptor(),
				UUID.randomUUID().toString()
				, new FileSystemResource(file)
		);
	}

	public void createLargeFile() {
		try(FileOutputStream out = new FileOutputStream(filePath)) {
			byte[] b = new byte[1024];
			for (int i = 0; i < 1024; i++) {
				b[i] = 'a';
			}
			int kb = 50 * 1024; // 50M
			for (int j = 0; j < kb; j++) {
				out.write(b);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private RestTemplate template() {
		SimpleClientHttpRequestFactory clientHttpRequestFactory
				= new SimpleClientHttpRequestFactory();

		clientHttpRequestFactory.setReadTimeout(600_000);// 10 mins
		clientHttpRequestFactory.setConnectTimeout(600_000);// 10 mins
		clientHttpRequestFactory.setBufferRequestBody(false);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		return restTemplate;
	}

	private RestTemplate templateWithInterceptor() {
		RestTemplate template = template();
		template.getInterceptors().add(new BasicAuthenticationInterceptor("username", "password"));
		return template;
	}

}
