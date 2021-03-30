package com.dxunited.core.auth.interceptors;


import com.dxunited.core.auth.constant.AuthConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return areValidHeaders(request);
    }

   /* @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
    }*/

    private boolean areValidHeaders(HttpServletRequest request) {
        Set<String> requiredHeaders = new HashSet<>();
        //requiredHeaders.add(X_CORRELATION_ID);
        requiredHeaders.add(AuthConstants.TENANT_ID);
        Set<String> headersPresent = new HashSet<>();
        /*if(request.getHeader("sec-fetch-mode")!=null && request.getHeader("sec-fetch-mode").equals("cors"))
        {
            String[] split = request.getHeader("access-control-request-headers").split(",");
            headersPresent = Arrays.stream(split).filter(requiredHeaders::contains)
                    .collect(toSet());
        } else {
            headersPresent = Collections.list(request.getHeaderNames())
                    .stream()
                    .filter(requiredHeaders::contains)
                    .collect(toSet());
        }

        if (headersPresent.size() != requiredHeaders.size()) {
            requiredHeaders.removeAll(headersPresent);
            throw new HttpHeaderException(ErrorCode.HEADERS_MISSING.getCode(), String.join(",", requiredHeaders));
        }
*/
        /*Set<String> invalidHeaders = headersPresent.stream()
                .filter(header -> !(StringUtils.hasText(request.getHeader(header)) *//*&& isValidHeader(header, request.getHeader(header))*//*))
                .collect(toSet());

        if (!invalidHeaders.isEmpty()) {
            throw new HttpHeaderException(ErrorCode.HEADERS_INVALID.getCode(), String.join(",", invalidHeaders));
        }*/
        return true;
    }

    private boolean isValidHeader(String header, String headerValue) {
        if (AuthConstants.X_CORRELATION_ID.equals(header))
            return isValidUUID(headerValue);
        else return true;
    }

    private boolean isValidUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
