package com.iobuilders.application;

import com.iobuilders.domain.JwtGeneratorService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtGeneratorServiceImpl implements JwtGeneratorService {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public JwtToken generateToken(User user) {
        String jwtToken = Jwts.builder().setSubject(user.userName()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, secret).compact();
        return new JwtToken(jwtToken);
    }
}