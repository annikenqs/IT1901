package json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import core.User;

public class UsersPersistence {

    private String filePath = "../core/src/main/java/resources/users.json";
    private ObjectMapper objectMapper;

    public UsersPersistence() {
        objectMapper = new ObjectMapper();
    }

    public String readFromUser(String filePath){
        File jsonFile = new File(filePath);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonFile);
            JsonNode usersString = jsonNode.get("password");
            return usersString.asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> readFromUsers() {
        File jsonFile = new File(filePath);
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonFile);
            JsonNode usersArray = jsonNode.get("users");
            List<String> userList = new ArrayList<>();
            if (usersArray.isArray()) {
                for (JsonNode element : usersArray) {
                    userList.add(element.asText());
                }
            }
            return userList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void createNewUser(User user) throws StreamWriteException, DatabindException, IOException{
        String username = user.getUsername();
        objectMapper.writeValue(new File("../core/src/main/java/resources/" + username + ".json"), user);
    }

    public void writeToUsers(User user) throws IOException {
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        ArrayNode usersNode = (ArrayNode) rootNode.get("users");
        String newUser = user.getUsername();
        usersNode.add(newUser);
        objectMapper.writeValue(new File(filePath), rootNode);
    }
}
