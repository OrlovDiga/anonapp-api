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
 * The class provides a service for working with files.
 *
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

    /**
     * This method return url for the uploaded file.
     *
     * @param fileDTO contains information to create a file.
     *
     * @return {@link String} an url, that can be accessed to get the uploaded file.
     */
    @PostMapping
    public String uploadPhoto(@RequestBody FileDTO fileDTO) throws IOException {
        String name = fileDTO.encodeAndSaveFile(contentPath);
        LOG.info("Load file with name {}", name);
        return urlPrefix + name;
    }

    /**
     * This method gives access to an existing JPEG file in the storage.
     *
     * @param fileName you want to receive.
     *
     * @return {@link HttpEntity<Byte[]>} response.
     * This response contains content type = jpeg in headers and file payload in the body.
     */
    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public HttpEntity<byte[]> getPhoto(@PathVariable("fileName") String fileName) throws IOException {
        LOG.info("Get photo with name {}", fileName);
        byte[] image = FileUtils.readFileToByteArray(new File(contentPath + fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}
