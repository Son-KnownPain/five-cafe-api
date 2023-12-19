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
        public static final String IMPORT = "/import";
        public static final String TIMEKEEPING = "/timekeeping";
        public static final String EMP_SALARY = "/emp-salary";
        public static final String OUTBOUND = "/outbound";
        public static final String BILL = "/bill";
        public static final String ORDERING = "/ordering";
        public static final String CREATE_OUTBOUND = "/create-outbound";
        public static final String MY_BILLS = "/my-bills";
        public static final String MY_TIMEKEEPINGS = "/my-timekeepings";
        public static final String MY_SALARIES = "/my-salaries";
        public static final String MY_OUTBOUNDS = "/my-outbounds";
        
        public static final String GREET1 = "/greet";
        public static final String GREET2 = "/";
        public static final String GREET3 = "";
        
        public static final String COST_STATISTIC = "/cost-statistic";

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
            signInUrls.add(EMP_SALARY);
            signInUrls.add(TIMEKEEPING);
            signInUrls.add(OUTBOUND);
            signInUrls.add(BILL);
            signInUrls.add(ORDERING);
            signInUrls.add(CREATE_OUTBOUND);
            signInUrls.add(MY_BILLS);
            signInUrls.add(MY_TIMEKEEPINGS);
            signInUrls.add(MY_SALARIES);
            signInUrls.add(MY_OUTBOUNDS);
            signInUrls.add(COST_STATISTIC);
            
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
