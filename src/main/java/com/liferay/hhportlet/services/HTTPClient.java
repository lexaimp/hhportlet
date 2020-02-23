package com.liferay.hhportlet.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class HTTPClient {

    public static String getContentFromRequest(String requestUri) {
        if (requestUri == null) {
            throw new IllegalArgumentException("requestUri cannot be null");
        }
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(requestUri);
        try {
            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContentFromRequest(String requestUri, Map<String, Integer> arguments) {
        if (requestUri == null || arguments == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        StringBuilder requestUriWithArguments = new StringBuilder(requestUri);
        requestUriWithArguments.append("?");

        arguments.forEach((k, v) -> requestUriWithArguments.append(k).append("=").append(v).append("&"));
        requestUriWithArguments.deleteCharAt(requestUriWithArguments.length() - 1);
        return getContentFromRequest(requestUriWithArguments.toString());
    }
}
