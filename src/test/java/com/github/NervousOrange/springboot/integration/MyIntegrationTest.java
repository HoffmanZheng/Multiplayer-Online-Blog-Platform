package com.github.NervousOrange.springboot.integration;

import com.github.NervousOrange.springboot.Application;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {
    @Inject
    Environment environment;

    @Test
    public void SmokeTest() throws IOException {
        String hostPort = environment.getProperty("local.server.port");
        System.out.println(hostPort);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        testFailedAuth(hostPort, httpclient);

        JSONObject json = testRegister(hostPort, httpclient);

        testFailedAuth(hostPort, httpclient);

        testLogin(hostPort, httpclient, json);

        testSuccessfulAuth(hostPort, httpclient);

    }

    private void testSuccessfulAuth(String hostPort, CloseableHttpClient httpclient) throws IOException {
        HttpGet httpGet = new HttpGet("http://localhost:" + hostPort + "/auth");
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            Assertions.assertTrue(response.getStatusLine().toString().contains("200"));
            String entity = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println(entity);
            Assertions.assertTrue(entity.contains("\"status\":\"ok\""));
            Assertions.assertTrue(entity.contains("\"isLogin\":true"));
            Assertions.assertTrue(entity.contains("chenghao"));
        }
    }

    private void testFailedAuth(String hostPort, CloseableHttpClient httpclient) throws IOException {
        HttpGet httpGet = new HttpGet("http://localhost:" + hostPort + "/auth");
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            Assertions.assertTrue(response.getStatusLine().toString().contains("200"));
            String entity = EntityUtils.toString(response.getEntity());
            System.out.println(entity);
            Assertions.assertTrue(entity.contains("\"status\":\"ok\""));
            Assertions.assertTrue(entity.contains("\"isLogin\":false"));
        }
    }

    private void testLogin(String hostPort, CloseableHttpClient httpclient, JSONObject json) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:" + hostPort +"/auth/login");
        httpPost.setHeader("Content-Type", "application/json");
        HttpEntity e = new ByteArrayEntity(json.toString().getBytes(StandardCharsets.UTF_8));
        httpPost.setEntity(e);

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getStatusLine().toString());
            Assertions.assertTrue(response.getStatusLine().toString().contains("200"));
            String entity = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println(entity);
            Assertions.assertTrue(entity.contains("登录成功"));
            Assertions.assertTrue(entity.contains("chenghao"));
        }
    }

    private JSONObject testRegister(String hostPort, CloseableHttpClient httpclient) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:" + hostPort +"/auth/register");
        httpPost.setHeader("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("username", "chenghao");
        json.put("password", "encoded");
        HttpEntity e = new ByteArrayEntity(json.toString().getBytes(StandardCharsets.UTF_8));
        httpPost.setEntity(e);

        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
            System.out.println(response2.getStatusLine().toString());
            Assertions.assertTrue(response2.getStatusLine().toString().contains("200"));
            String entity = EntityUtils.toString(response2.getEntity(), StandardCharsets.UTF_8);
            System.out.println(entity);
            Assertions.assertTrue(entity.contains("注册成功"));
            Assertions.assertTrue(entity.contains("chenghao"));
        }
        return json;
    }


}
