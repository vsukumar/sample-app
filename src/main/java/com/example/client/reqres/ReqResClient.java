package com.example.client.reqres;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReqResClient {

    CloseableHttpClient client = HttpClients.createDefault();

    @Autowired
    private ObjectMapper objectMapper;
    
    public void init(String auth){

    }


    public void createReqResUser(String firstName)  {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://reqres.in/api/users");

        ReqResUser reqResUser = new ReqResUser.ReqResUserBuilder()
                .first_name(firstName)
                .build();

        String object = "{\"name\": \"morpheus\",\"job\": \"leader\"}";


        StringEntity requestEntity = new StringEntity(
                object,
                ContentType.APPLICATION_JSON);


//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("username", "John"));
//        params.add(new BasicNameValuePair("password", "pass"));
        httpPost.setEntity(requestEntity);

        try {
            CloseableHttpResponse response = client.execute(httpPost);

            System.out.println("response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ReqResUser getReqResUser(long id)  {
        ReqResUser reqResUser = null;
        try {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://reqres.in/api/users/2");

        CloseableHttpResponse response = null;

            response = client.execute(httpGet);


//        System.out.println(response.getEntity());

            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("response string : " +responseString);

            ReqResWrapper reqResWrapper = objectMapper.readValue(responseString, ReqResWrapper.class);

            System.out.println("reqResUser : " +reqResWrapper);
//            objectMapper.readValu

//        reqResUser = new ReqResUser.ReqResUserBuilder()
//                .firstName("")
//                .build();

            reqResUser = reqResWrapper.getData();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reqResUser;
    }
    //https://reqres.in/api/users/2

    //{"data":{"id":2,"email":"janet.weaver@reqres.in","first_name":"Janet","last_name":"Weaver","avatar":"https://reqres.in/img/faces/2-image.jpg"},"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}
}
