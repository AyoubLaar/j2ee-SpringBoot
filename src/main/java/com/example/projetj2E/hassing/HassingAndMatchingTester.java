package com.example.projetj2E.hassing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HassingAndMatchingTester {
    public static String passwordtohash(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);

    }

    public static boolean passwordMatching(String hashedPassword,String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }


}