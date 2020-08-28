package anonapp.api.controller;

import anonapp.api.dto.FileDTO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(path = "/resources")
public class ResourceController {

    @Value("${resource.path.images}")
    private String contentPath;
    @Value("${resource.prefix.url}")
    private String urlPrefix;
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @PostMapping
    public String uploadPhoto(@RequestBody FileDTO fileDTO) throws IOException {
        String name = fileDTO.encodeAndSaveFile();
        LOG.info("Load file with name {}", name);
        return urlPrefix + name;
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public HttpEntity<byte[]> getPhoto(@PathVariable("fileName") String fileName) throws IOException {
        LOG.info("Get photo with name {}", fileName);
        byte[] image = FileUtils.readFileToByteArray(new File(contentPath + fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}
