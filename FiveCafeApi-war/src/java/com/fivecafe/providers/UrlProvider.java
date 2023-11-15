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
    
    public class ProductCategory {
        public static final String PREFIX = "/pro-category";
        
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        public static final String ADD = "/add";
        public static final String STORE = "/store";
        
        
    }
    
    public List<AuthUrl> getAllAuthUrl() {
        ArrayList<AuthUrl> result = new ArrayList<>();
        result.add(new Employee());
        return result;
    }
}


