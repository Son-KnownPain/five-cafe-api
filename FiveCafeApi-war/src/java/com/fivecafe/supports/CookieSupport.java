package com.fivecafe.supports;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieSupport {
    public static Cookie getSpecificCookie(HttpServletRequest request, String specificName) {
        try {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(specificName)) return cookie;
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
