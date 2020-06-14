package com.example.team19.service;

import com.example.team19.model.TokenInfo;

public interface TokenInfoService {

    boolean checkIfExpired();

    TokenInfo save(TokenInfo tokenInfo);
    TokenInfo getToken();
}
