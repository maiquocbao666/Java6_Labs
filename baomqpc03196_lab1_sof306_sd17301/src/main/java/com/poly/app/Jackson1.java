package com.poly.app;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Bài 4
public class Jackson1 {

    public static void main(String[] agrs) {
        try {
            // demo1();
            demo2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void demo2() throws Exception {
        String path = "baomqpc03196_lab1_sof306_sd17301\\src\\main\\resources\\students.json";
        // ObjectMapper là một class trong thư viện Jackson được sử dụng để chuyển đổi
        // dữ liệu giữa các định dạng, chẳng hạn như giữa JSON và Java Object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode students = mapper.readTree(new File(path));
        students.iterator().forEachRemaining(student -> {
            System.out.println(">> Name: " + student.get("name").asText());
        });
    }

    private static void demo1() throws Exception {
        // String json = "{\r\n"
        // + " \"name\": \"Nguyễn Văn Tèo\",\r\n"
        // + " \"gender\": true,\r\n"
        // + " \"marks\": 7.5,\r\n"
        // + " \"contact\": {\r\n"
        // + " \"email\": \"teonv@gmail.com\",\r\n"
        // + " \"phone\": \"0913745789\"\r\n"
        // + " },\r\n"
        // + " \"subjects\": [\"WEB205\", \"COM108\"]\r\n"
        // + "}";

        String path = "baomqpc03196_lab1_sof306_sd17301\\src\\main\\resources\\student.json";

        // System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();
        // JsonNode student = mapper.readTree(json);
        JsonNode student = mapper.readTree(new File(path));

        System.out.println(">> Name: " + student.get("name").asText());
        System.out.println(">> Marks: " + student.get("marks").asText());
        System.out.println(">> Gender: " + student.get("gender").asText());
        System.out.println(">> Email: " + student.get("contact").get("email").asText());
        System.out.println(">> Phone: " + student.findValue("phone").asText());
        student.get("subjects").iterator().forEachRemaining(subject -> {
            System.out.println(">> Subject: " + subject.asText());
        });

    }
}