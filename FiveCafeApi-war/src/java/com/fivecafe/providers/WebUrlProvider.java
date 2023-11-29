package com.fivecafe.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebUrlProvider {
    public class Site implements WebAuthUrl {
        public static final String LOGIN = "/login";
        
        public static final String GREET1 = "/greet";
        public static final String GREET2 = "/";
        public static final String GREET3 = "";

        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(GREET1);
            signInUrls.add(GREET2);
            signInUrls.add(GREET3);
            
            return signInUrls;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            return null;
        }
        
    }
    
    public List<WebAuthUrl> getAllAuthUrl() {
        ArrayList<WebAuthUrl> result = new ArrayList<>();
        result.add(new Site());

        return result;
    }
}