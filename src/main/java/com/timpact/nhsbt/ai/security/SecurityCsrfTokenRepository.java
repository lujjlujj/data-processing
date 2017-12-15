/*
 * Copyright (c) 2017 T-IMPACT Development Team. All rights reserved.
 *
 *  This software is only to be used for the purpose for which it has been
 *  provided. No part of it is to be reproduced, disassembled, transmitted,
 *  stored in a retrieval system, nor translated in any human or computer
 *  language in any way for any purposes whatsoever without the prior written
 *  consent of the Sprinter Development Team.
 *  Infringement of copyright is a serious civil and criminal offence, which can
 *  result in heavy fines and payment of substantial damages.
 */
package com.timpact.nhsbt.ai.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>SecurityCsrfTokenRepository</code>
 * <p>
 * <p>
 * The repository to handle csrf token for angular-js framework
 * </p>
 */
@Component
public class SecurityCsrfTokenRepository implements CsrfTokenRepository {

    public static final String CSRF_PARAMETER_NAME = "_csrf";

    public static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    private final Map<String, CsrfToken> tokenRepository = new ConcurrentHashMap<>();

    /**
     * Constructs <code>SecurityCsrfTokenRepository</code>
     */
    public SecurityCsrfTokenRepository() {
        //log.info("Creating {}", CustomCsrfTokenRepository.class.getSimpleName());
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(CSRF_HEADER_NAME, CSRF_PARAMETER_NAME, createNewToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String key = getKey(request);
        if (key == null)
            return;

        if (token == null) {
            tokenRepository.remove(key);
        } else {
            tokenRepository.put(key, token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String key = getKey(request);
        return key == null ? null : tokenRepository.get(key);
    }

    private String getKey(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }
}