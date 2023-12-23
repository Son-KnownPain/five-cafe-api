package com.fivecafe.providers;

import com.fivecafe.enums.HardRoles;
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
            
            return signInUrls;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(GREET1);
            ownerUrls.add(GREET2);
            ownerUrls.add(GREET3);
            ownerUrls.add(ROLE);
            ownerUrls.add(EMPLOYEE);
            ownerUrls.add(MATERIAL);
            ownerUrls.add(MATERIAL_CATEGORIES);
            ownerUrls.add(PRODUCT);
            ownerUrls.add(PRODUCT_CATEGORIES);
            ownerUrls.add(EMP_SALARY);
            ownerUrls.add(SHIFTS);
            ownerUrls.add(SUPPLIER);
            ownerUrls.add(TIMEKEEPING);
            ownerUrls.add(OUTBOUND);
            ownerUrls.add(BILL);
            ownerUrls.add(BILL_STATUS);
            ownerUrls.add(COST_STATISTIC);
            ownerUrls.add(ORDERING);
            ownerUrls.add(CREATE_OUTBOUND);
            ownerUrls.add(MY_BILLS);
            ownerUrls.add(MY_TIMEKEEPINGS);
            ownerUrls.add(MY_SALARIES);
            ownerUrls.add(MY_OUTBOUNDS);
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            counterStaffUrls.add(GREET1);
            counterStaffUrls.add(GREET2);
            counterStaffUrls.add(GREET3);
            counterStaffUrls.add(ORDERING);
            counterStaffUrls.add(CREATE_OUTBOUND);
            counterStaffUrls.add(MY_BILLS);
            counterStaffUrls.add(MY_TIMEKEEPINGS);
            counterStaffUrls.add(MY_SALARIES);
            counterStaffUrls.add(MY_OUTBOUNDS);
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            servingStaffUrls.add(GREET1);
            servingStaffUrls.add(GREET2);
            servingStaffUrls.add(GREET3);
            servingStaffUrls.add(MY_BILLS);
            servingStaffUrls.add(MY_TIMEKEEPINGS);
            servingStaffUrls.add(MY_SALARIES);
            servingStaffUrls.add(MY_OUTBOUNDS);
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
        
    }
    
    public class Menu implements WebAuthUrl {
        public static final String MENU = "/menu";

        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            return roleUrls;
        }
        
    }
    
    public List<WebAuthUrl> getAllAuthUrl() {
        ArrayList<WebAuthUrl> result = new ArrayList<>();
        result.add(new Site());

        return result;
    }
}
