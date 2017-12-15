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

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <code>UrlAuthenticationSuccessHandler</code>
 * <p>
 * <p>
 * The handler for the success scenario of authentication with url.
 * </p>
 */
public class UrlAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success to auth.");
        String requestType = request.getHeader("x-requested-with");
        if (!StringUtils.isEmpty(requestType)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print("{\"success\": \"true\"}");
        } else {
            setDefaultTargetUrl("/");
            setAlwaysUseDefaultTargetUrl(true);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
