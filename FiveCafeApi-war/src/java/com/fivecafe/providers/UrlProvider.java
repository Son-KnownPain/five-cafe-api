package com.fivecafe.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UrlProvider {
    public static final String API_PREFIX = "/api";
    
    private String addApiPrefix(final String PATH) {
        return API_PREFIX + PATH;
    }
    
    public class Employee implements AuthUrl {
        public static final String PREFIX = "/employee";
        
        // NONE SIGN IN
        public static final String TEST = "/test";
        
        // NEED TO SIGN IN
        public static final String ALL = "/all"; 
        
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
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
    
    public class Role implements AuthUrl {
        public static final String PREFIX = "/role";
        
        // NONE SIGN IN
        
        // NEED TO SIGN IN
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
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
        result.add(new Employee());
        result.add(new Role());

        return result;
    }
}


