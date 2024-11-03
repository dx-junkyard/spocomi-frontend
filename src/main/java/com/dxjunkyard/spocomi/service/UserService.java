package com.dxjunkyard.spocomi.service;

import com.dxjunkyard.spocomi.api.client.LineLoginRestClient;
import com.dxjunkyard.spocomi.api.client.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private LineLoginRestClient lineLoginRestClient;

    @Autowired
    private UserRestClient userRestClient;

    /*
     * lineIdからトークンを取得する
     */
    public String createUserIfNotExist(String lineId) {
        return userRestClient.getTokenByLineId(lineId);
    }



    public String lineAuth(String code) {
        String id_token = lineLoginRestClient.lineAuth(code);
        return id_token;
    }
}
