package com.example.team19.service.impl;

import com.example.team19.model.TokenInfo;
import com.example.team19.repository.TokenInfoRepository;
import com.example.team19.service.TokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenInfoServiceImpl implements TokenInfoService {

    @Autowired
    private TokenInfoRepository tokenInfoRepository;


    @Override
    public boolean checkIfExpired() {
        TokenInfo tokenInfo = tokenInfoRepository.findById(1L).orElse(null);

        if(tokenInfo == null){
            System.out.println("Ne postoji");
            return true;
        }

        Date date = new Date(System.currentTimeMillis());

        if(date.after(tokenInfo.getExpires())){
            System.out.println("Istekao");
            return true;
        }

        return false;
    }

    @Override
    public TokenInfo save(TokenInfo tokenInfo) {
        TokenInfo tokenInfoExists = tokenInfoRepository.findById(1L).orElse(null);
        if(tokenInfo != null){
            tokenInfo = tokenInfoRepository.save(tokenInfo);
        }else{
            tokenInfoExists.setExpires(tokenInfo.getExpires());
            tokenInfoExists.setToken(tokenInfo.getToken());
            tokenInfo = tokenInfoRepository.save(tokenInfoExists);
        }


        return tokenInfo;
    }

    @Override
    public TokenInfo getToken() {
        return tokenInfoRepository.findById(1L).orElse(null);
    }
}
