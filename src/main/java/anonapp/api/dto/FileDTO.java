package anonapp.api.dto;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * This class represents a java object that will be deserialized from json.
 *
 * @author Orlov Diga
 */
public class FileDTO {
    private String extension;
    private String data;

    /**
     * This method is for decoding and saving the file to the host.
     *
     * @param path to the directory where to save the file.
     * @return {@link String} saved file name.
     */
    public String encodeAndSaveFile(String path) throws IOException {
        byte[] encoded = Base64.decodeBase64(data);
        System.out.println("extension = " + extension);
        File file = new File(path + UUID.randomUUID() + extension);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(encoded);
        }

        System.out.println(file.toPath());
        System.out.println(file.toURI());
        return file.getName();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
