package anonapp.api.controller;

import anonapp.api.dto.FileDTO;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Orlov Diga
 */
@RestController
@RequestMapping(path = "/resources")
public class ResourceController {

    private static final String CONTENT_PATH = "src/main/resources/static/files/";
    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @PostMapping
    public String uploadPhoto(@RequestBody FileDTO fileDTO) throws IOException {
        LOG.info("Load file with extension {}", fileDTO.getExtension());
        String name = fileDTO.encodeAndSaveFile();
        LOG.info("Load file with name {}", name);
        return "http://localhost:8080/resources/" + name;
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public HttpEntity<byte[]> getPhoto(@PathVariable("fileName") String fileName) throws IOException {
        byte[] image = FileUtils.readFileToByteArray(new File(CONTENT_PATH + fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}
