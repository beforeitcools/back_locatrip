package com.ohgiraffers.jenkins_test_app.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConvertUtil {

    // 전달받은 객체를 JSON 형태로 변환한 후 다시 JSON 객체로 파싱하는 메서드
    public static Object convertObjectToJsonObject(Object obj){

        ObjectMapper mapper = new ObjectMapper(); // ObjectMapper: Java 객체를 JSON 문자열로 변환하는 Jackson 라이브러리의 객체
        JSONParser parser = new JSONParser(); // JSONParser: JSON 문자열을 JSON 객체로 파싱하는 라이브러리의 객체
        String convertJsonString; // 변환된 JSON 문자열을 저장할 변수
        Object convertObj; // 최종적으로 JSON 객체로 변환된 결과를 저장할 변수

        try {
            convertJsonString = mapper.writeValueAsString(obj); // 1. ObjectMapper를 사용하여 객체를 JSON 문자열로 변환
            convertObj = parser.parse(convertJsonString); // 2. JSONParser를 사용하여 JSON 문자열을 JSON 객체로 파싱
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // 3. JSON 변환 중 예외 발생 시 RuntimeException으로 전환하여 예외 처리
        } catch (ParseException e) {
            throw new RuntimeException(e); // 4. JSON 파싱 중 예외 발생 시 RuntimeException으로 전환하여 예외 처리
        }

        return convertObj; // 5. JSON 객체로 변환된 결과 반환
    }
}
