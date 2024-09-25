package okestro.hjpark.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BoardService {

    public Map<String, Object> boardList() {
        File file = new File("src/main/java/okestro/hjpark/temp/boardData.json");

        Map<String, Object> jsonData = new HashMap();
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonData = mapper.readValue(file, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonData;
    }

    public Map<String, Object> boardDetail(int id) {
        File file = new File("src/main/java/okestro/hjpark/temp/boardData.json");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Object>> jsonData = null;

        try {
            // JSON 객체를 Map<String, Map>으로 파싱
            jsonData = mapper.readValue(file, new TypeReference<Map<String, Map<String, Object>>>() {});

            // 특정 id 값을 가진 행 찾기
            String idKey = String.valueOf(id);
            if (jsonData.containsKey(idKey)) {
                return jsonData.get(idKey);  // id 값에 해당하는 행을 반환
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
