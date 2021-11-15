package com.example.task.service;

import com.example.task.constants.SecurityConstants;
import com.example.task.data.model.Client;
import com.example.task.data.model.ClientRequest;
import com.example.task.data.repository.ClientRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

@Service
public class ClientJsonTokenImpl implements ClientJsonToken {
    @Autowired
    ClientRepository clientRepository;

    private String passwordDB;
    private String generateToken;
    public static final String GENERATED_TOKEN = "generatedToken";

    String query = "SELECT * FROM client";
    DBWorker dbWorker = new DBWorker();


    Statement statement;

    {
        try {
            statement = dbWorker.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                passwordDB = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String createToken() {
        Client client = new Client();
        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
        generateToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(client.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .compact();
        return generateToken;
    }

    @Override
    public String sendToken(String passwordFromClient) {
        createToken();
        if (passwordFromClient.equals(passwordDB)) {
            return jsonFormat(GENERATED_TOKEN, generateToken);
        } else return "Password was incorrect";
    }


    private String jsonFormat(String key, String value) {
        return new JSONObject().put(key, value).toString();
    }

    String token;

    @Override
    public String validateToken(HttpServletRequest request, ClientRequest clientRequest) {
        token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (token.equals(generateToken)) {
            saveMessage(clientRequest);
            return "Save message to DB";
        } else
            return "Incorrect token";
    }

    @Override
    public void saveMessage(ClientRequest clientRequest) {
        Client client = new Client();
        client.setMessage(client.getMessage());
        clientRepository.save(client);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                var signingKey = SecurityConstants.JWT_SECRET.getBytes();

                var parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                var username = parsedToken
                        .getBody()
                        .getSubject();

            } catch (ExpiredJwtException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
