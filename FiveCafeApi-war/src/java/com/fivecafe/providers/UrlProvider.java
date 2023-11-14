package com.fivecafe.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UrlProvider {
    public static final String API_PREFIX = "/api";
    
    private String addApiPrefix(final String PATH) {
        return API_PREFIX + PATH;
    }
    
    public class User implements AuthUrl {
        public static final String PREFIX = "/user";
        
        // NONE SIGN IN
        public static final String SIGN_UP = "/sign-up";
        public static final String SIGN_IN = "/sign-in";
        public static final String FORGOT_PASSWORD = "/forgot-password";
        
        // NEED TO SIGN IN
        public static final String GET_USER_INFO = "/info";
        public static final String CHANGE_USER_INFO = "/change-info";
        public static final String SIGN_OUT = "/sign-out";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(addApiPrefix(addUserPrefix(GET_USER_INFO)));
            signInUrls.add(addApiPrefix(addUserPrefix(CHANGE_USER_INFO)));
            signInUrls.add(addApiPrefix(addUserPrefix(SIGN_OUT)));
            
            return signInUrls;
        }
        
        private String addUserPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            return null;
        }
    }
    
    public List<AuthUrl> getAllAuthUrl() {
        ArrayList<AuthUrl> result = new ArrayList<>();
        result.add(new User());
        return result;
    }
}


