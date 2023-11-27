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
    
    public List<AuthUrl> getAllAuthUrl() {
        ArrayList<AuthUrl> result = new ArrayList<>();
        result.add(new Employee());
        result.add(new Role());

        return result;
    }
}


