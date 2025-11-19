package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class ProductIdHandler {

    private static final String FILE_PATH = "src/test/resources/productId.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // حفظ الـ ID
    public static synchronized void saveProductId(String id) throws IOException {
        File file = new File(FILE_PATH);
        ObjectNode root = mapper.createObjectNode();
        root.put("id", id);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
    }

    // قراءة الـ ID
    public static synchronized String getProductId() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return null;

        ObjectNode root = (ObjectNode) mapper.readTree(file);
        return root.get("id").asText();
    }


}
