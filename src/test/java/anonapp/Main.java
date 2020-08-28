package anonapp;

import anonapp.api.dto.ChatMessage;
import anonapp.api.dto.FileDTO;
import anonapp.api.dto.SocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64 ;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Orlov Diga
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //ObjectMapper mapper = new ObjectMapper();
        //String res = "{id: da09dd58-43a4-4359-9d84-80f29bec983d, text: Hello, World!, image: null, video: null, createdAt: 1598094974063, user: {uid: 12345678, name: Fayeed, firstName: Fayeed, lastName: Pawaskar, avatar: https://www.wrappixel.com/ampleadmin/assets/images/users/4.jpg, containerColor: null, color: null, customProperties: null}, quickReplies: null, customProperties: null}";
        //JSONObject jsonObject = new JSONObject(res);
        //System.out.println(res.substring(63));
        //System.out.println(jsonObject.getString("id"));
        //ChatMessage msg = mapper.readValue("{id: da09dd58-43a4-4359-9d84-80f29bec983d, text: Hello, World!, image: null, video: null, createdAt: 1598094974063, user: {uid: 12345678, name: Fayeed, firstName: Fayeed, lastName: Pawaskar, avatar: https://www.wrappixel.com/ampleadmin/assets/images/users/4.jpg, containerColor: null, color: null, customProperties: null}, quickReplies: null, customProperties: null}", ChatMessage.class);
       /* String message = "{\n" +
                "    \"type\": \"NEXT\",\n" +
                "    \"chatMessage\": {\n" +
                "        \"id\": 1,\n" +
                "        \"text\": \"\",\n" +
                "        \"user\": {\n" +
                "            \"uid\": \"132-213213\",\n" +
                "            \"name\": \"Walf\",\n" +
                "            \"avatar\": \"pictureAvatar\"\n" +
                "        },\n" +
                "        \"image\": \"referenceToImage\",\n" +
                "        \"video\": \"referenceToVideo\"\n" +
                "    }\n" +
                "}";

        SocketMessage msg = SocketMessage.fromJson(message);
        ChatMessage chatMessage = msg.getChatMessage();

        switch (msg.getType()) {
            case NEXT: {
                System.out.println("next");
            }
            default: {
                System.out.println("default");
            }

        }
        System.out.println(chatMessage.getUser());

        System.out.println(msg.getType());
*/
        File file = new File("/Users/macbook/Desktop/29.jpg");

        byte[] fileContent = Files.readAllBytes(file.toPath());
        //byte[] row = Base64.encodeBase64(fileContent);
        String data = Base64.getEncoder().encodeToString(fileContent);

        FileDTO fileDTO = new FileDTO();
        fileDTO.setData(data);
        fileDTO.setExtension("jpg");
        String res = fileDTO.encodeAndSaveFile();

        System.out.println(res);

    }
}
