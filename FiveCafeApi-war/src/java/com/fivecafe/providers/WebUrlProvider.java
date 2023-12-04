package com.fivecafe.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebUrlProvider {
    public class Site implements WebAuthUrl {
        public static final String LOGIN = "/login";
        
        public static final String ROLE = "/role";
        public static final String BILL_STATUS = "/bill-status";
        public static final String PRODUCT_CATEGORIES = "/pro-category";
        public static final String MATERIAL_CATEGORIES = "/mat-category";
        public static final String SHIFTS = "/shift";
        public static final String SUPPLIER = "/supplier";
        public static final String EMPLOYEE = "/employee";
        public static final String MATERIAL = "/material";
        public static final String PRODUCT = "/product";
        
        public static final String GREET1 = "/greet";
        public static final String GREET2 = "/";
        public static final String GREET3 = "";

        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(GREET1);
            signInUrls.add(GREET2);
            signInUrls.add(GREET3);
            signInUrls.add(ROLE);
            signInUrls.add(EMPLOYEE);
            signInUrls.add(MATERIAL);
            signInUrls.add(PRODUCT);
            
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
