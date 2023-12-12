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
        public static final String LOGIN = "/login";
        
        // NEED TO SIGN IN
        public static final String ALL = "/all"; 
        public static final String SEARCH = "/search";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        public static final String LOGOUT = "/logout";
        
        public static final String INFO = "/info";
        
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(addApiPrefix(addEmployeePrefix(INFO)));
            signInUrls.add(addApiPrefix(addEmployeePrefix(LOGOUT)));
            return signInUrls;
        }
        
        private String addEmployeePrefix(final String PATH) {
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
    
    public class Product implements AuthUrl{
        public static final String PREFIX = "/product";
        
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
     
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
    
    public class ProductCategory implements AuthUrl{
        public static final String PREFIX = "/pro-category";
        
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
     
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
    
    public class Outbound implements AuthUrl{
        public static final String PREFIX = "/outbound";
        
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String STORE_MAT_ITEM = "/store-mat-item";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String DELETE_MAT_ITEM = "/delete-mat-item";
        public static final String SEARCH = "/search";
        
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
    
    public class Supplier implements AuthUrl{
        public static final String PREFIX = "/supplier";
        
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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
    
    //Nhu Code O Day:
    public class MaterialCategory implements AuthUrl{
        public static final String PREFIX ="/mat-category";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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
    
    public class Material implements AuthUrl{
        public static final String PREFIX ="/material";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
        
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
    
    public class BillStatus implements AuthUrl{
        public static final String PREFIX ="/bill-sts";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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
    
     public class Bills implements AuthUrl{
        public static final String PREFIX ="/bill";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String STORE_PRO_ITEM = "/store-pro-item";
        public static final String UPDATE_PRO_ITEM = "/update-pro-item";
        public static final String DELETE_PRO_ITEM = "/delete-pro-item";
        public static final String DELETE_BILL = "/delete_bill";
        
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
    
    
    public class Import implements AuthUrl {
        public static final String PREFIX = "/import";
        
        // NONE SIGN IN
        
        // NEED TO SIGN IN
        public static final String ALL = "/all"; 
        public static final String STORE = "/store";
        public static final String STORE_MAT_ITEM = "/store-mat-item";
        public static final String UPDATE_MAT_ITEM = "/update-mat-item";
        public static final String DELETE_MAT_ITEM = "/delete-mat-item";
        public static final String DELETE_IMPORT = "/delete_import";
        
        
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
    
    public class Shift implements AuthUrl{
        public static final String PREFIX ="/shift";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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
    
    public class EmployeeTimeKeeping implements AuthUrl{
        public static final String PREFIX ="/etk";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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
    
    public class EmployeeSalaries implements AuthUrl{
        public static final String PREFIX ="/salary";
        
        // NONE SIGN IN
        public static final String INDEX1 = "/index";
        public static final String INDEX2 = "/";
        public static final String INDEX3 = "";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String ADD_DETAILS = "/add-details";
        public static final String UPDATE_DETAIL_ITEM = "/update-detail-item";
        public static final String DELETE_DETAIL_ITEM = "/delete-detail-item";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
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


