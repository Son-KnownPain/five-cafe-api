package com.fivecafe.providers;

import com.fivecafe.enums.HardRoles;
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
        public static final String ORDERING = "/ordering";
        public static final String CREATE_OUTBOUND = "/create-outbound";
        public static final String ALL_MY_BILLS = "/all-my-bills";
        public static final String UPDATE_MY_BILL = "/update-my-bill";
        public static final String ADD_PRO_OF_BILL = "/add-pro-of-bill";
        public static final String UPDATE_PRO_OF_BILL = "/update-pro-of-bill";
        public static final String DELETE_PRO_OF_BILL = "/delete-pro-of-bill";
        public static final String ALL_MY_ETK = "/all-my-etk";
        public static final String ALL_MY_SALARIES = "/all-my-salaries";
        public static final String ALL_MY_OUTBOUNDS = "/all-my-outbounds";
        
        public static final String NAV = "/nav";
        public static final String NOT_SERVED_BILLS = "/not-served-bills";
        public static final String SERVED_BILL = "/served-bill";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addEmployeePrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ALL)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(SEARCH)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(STORE)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(LOGOUT)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(INFO)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ORDERING)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(CREATE_OUTBOUND)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_BILLS)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_MY_BILL)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_PRO_OF_BILL)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ADD_PRO_OF_BILL)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(DELETE_PRO_OF_BILL)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_ETK)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_OUTBOUNDS)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_SALARIES)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(NAV)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(NOT_SERVED_BILLS)));
            ownerUrls.add(addApiPrefix(addEmployeePrefix(SERVED_BILL)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(LOGOUT)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(INFO)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ORDERING)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(CREATE_OUTBOUND)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_BILLS)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_MY_BILL)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_PRO_OF_BILL)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ADD_PRO_OF_BILL)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(DELETE_PRO_OF_BILL)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_ETK)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_OUTBOUNDS)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_SALARIES)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(NAV)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(NOT_SERVED_BILLS)));
            counterStaffUrls.add(addApiPrefix(addEmployeePrefix(SERVED_BILL)));
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(LOGOUT)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(INFO)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_BILLS)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_MY_BILL)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(UPDATE_PRO_OF_BILL)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(ADD_PRO_OF_BILL)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(DELETE_PRO_OF_BILL)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_ETK)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_OUTBOUNDS)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(ALL_MY_SALARIES)));
            servingStaffUrls.add(addApiPrefix(addEmployeePrefix(NAV)));
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Statistic implements AuthUrl {
        public static final String PREFIX = "/statistic";
        
        // NONE SIGN IN
        
        // NEED TO SIGN IN
        public static final String REVENUE = "/revenue";
        public static final String COST = "/cost";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(addApiPrefix(addStatisticPrefix(REVENUE)));
            signInUrls.add(addApiPrefix(addStatisticPrefix(COST)));
            
            return signInUrls;
        }
        
        private String addStatisticPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addStatisticPrefix(REVENUE)));
            ownerUrls.add(addApiPrefix(addStatisticPrefix(COST)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
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
        
        private String addRolePrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addRolePrefix(ALL)));
            ownerUrls.add(addApiPrefix(addRolePrefix(STORE)));
            ownerUrls.add(addApiPrefix(addRolePrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addRolePrefix(DELETE)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Product implements AuthUrl{
        public static final String PREFIX = "/product";
        
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
        
        private String addProductPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addProductPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addProductPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addProductPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addProductPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addProductPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class ProductCategory implements AuthUrl{
        public static final String PREFIX = "/pro-category";
        
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
        
        private String addProCatePrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addProCatePrefix(ALL)));
            ownerUrls.add(addApiPrefix(addProCatePrefix(STORE)));
            ownerUrls.add(addApiPrefix(addProCatePrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addProCatePrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addProCatePrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Outbound implements AuthUrl{
        public static final String PREFIX = "/outbound";
        
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
        
        private String addOutboundPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addOutboundPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(STORE_MAT_ITEM)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(DELETE_MAT_ITEM)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addOutboundPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Supplier implements AuthUrl{
        public static final String PREFIX = "/supplier";
        
        public static final String ALL = "/all";
        public static final String STORE = "/store";
        public static final String SEARCH = "/search";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addSupplierPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addSupplierPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addSupplierPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addSupplierPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addSupplierPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addSupplierPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class MaterialCategory implements AuthUrl{
        public static final String PREFIX ="/mat-category";
        
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
        
        private String addMatCatePrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addMatCatePrefix(ALL)));
            ownerUrls.add(addApiPrefix(addMatCatePrefix(STORE)));
            ownerUrls.add(addApiPrefix(addMatCatePrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addMatCatePrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addMatCatePrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Material implements AuthUrl{
        public static final String PREFIX ="/material";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        public static final String GETQUANTITYINSTOCK = "/get-the-quantityinstock-below-five";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addMaterialPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addMaterialPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addMaterialPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addMaterialPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addMaterialPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addMaterialPrefix(SEARCH)));
            ownerUrls.add(addApiPrefix(addMaterialPrefix(GETQUANTITYINSTOCK)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class BillStatus implements AuthUrl{
        public static final String PREFIX ="/bill-sts";
        
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
        
        private String addBillStatusPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addBillStatusPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addBillStatusPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addBillStatusPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addBillStatusPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addBillStatusPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Bills implements AuthUrl{
        public static final String PREFIX ="/bill";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String STORE = "/store";
        public static final String UPDATE_BILL = "/update-bill";
        public static final String STORE_PRO_ITEM = "/store-pro-item";
        public static final String UPDATE_PRO_ITEM = "/update-pro-item";
        public static final String DELETE_PRO_ITEM = "/delete-pro-item";
        public static final String DELETE_BILL = "/delete_bill";
        public static final String SEARCH = "/search";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addBillPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addBillPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addBillPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addBillPrefix(UPDATE_BILL)));
            ownerUrls.add(addApiPrefix(addBillPrefix(STORE_PRO_ITEM)));
            ownerUrls.add(addApiPrefix(addBillPrefix(UPDATE_PRO_ITEM)));
            ownerUrls.add(addApiPrefix(addBillPrefix(DELETE_PRO_ITEM)));
            ownerUrls.add(addApiPrefix(addBillPrefix(DELETE_BILL)));
            ownerUrls.add(addApiPrefix(addBillPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Ordering implements AuthUrl {
        public static final String PREFIX ="/ordering";
        
        // NONE SIGN IN
        
        // NEED TO SIGN IN
        public static final String STORE = "/store";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            signInUrls.add(STORE);
            
            return signInUrls;
        }
        
        private String addOrderingPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addOrderingPrefix(STORE)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            counterStaffUrls.add(addApiPrefix(addOrderingPrefix(STORE)));
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
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
        public static final String SEARCH = "/search";
        
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addImportPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addImportPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addImportPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addImportPrefix(STORE_MAT_ITEM)));
            ownerUrls.add(addApiPrefix(addImportPrefix(UPDATE_MAT_ITEM)));
            ownerUrls.add(addApiPrefix(addImportPrefix(DELETE_MAT_ITEM)));
            ownerUrls.add(addApiPrefix(addImportPrefix(DELETE_IMPORT)));
            ownerUrls.add(addApiPrefix(addImportPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class Shift implements AuthUrl{
        public static final String PREFIX ="/shift";
        
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
        
        private String addShiftPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addShiftPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addShiftPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addShiftPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addShiftPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addShiftPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class EmployeeTimeKeeping implements AuthUrl{
        public static final String PREFIX ="/etk";
        
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
        
        private String addETKPrefix(final String PATH) {
            return PREFIX + PATH;
        }
        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addETKPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addETKPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addETKPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addETKPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addETKPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class EmployeeSalaries implements AuthUrl{
        public static final String PREFIX ="/salary";
        
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
        
        private String addEmpSalaryPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(ADD_DETAILS)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(UPDATE_DETAIL_ITEM)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(DELETE_DETAIL_ITEM)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addEmpSalaryPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public class MatToPro implements AuthUrl{
        public static final String PREFIX ="/mat-to-pro";
        
        // NEED TO SIGN IN
        public static final String ALL ="/all";
        public static final String BY_PRODUCT_ID ="/{id}";
        public static final String STORE = "/store";
        public static final String UPDATE = "/update";
        public static final String DELETE = "/delete";
        public static final String SEARCH = "/search";
        
        @Override
        public List<String> signInUrls() {
            ArrayList<String> signInUrls = new ArrayList<>();
            
            return signInUrls;
        }
        
        private String addMatToProPrefix(final String PATH) {
            return PREFIX + PATH;
        }

        
        @Override
        public HashMap<String, List<String>> roleUrls() {
            HashMap<String, List<String>> roleUrls = new HashMap<>();
            
            // Owner urls
            List<String> ownerUrls = new ArrayList<>();
            ownerUrls.add(addApiPrefix(addMatToProPrefix(ALL)));
            ownerUrls.add(addApiPrefix(addMatToProPrefix(BY_PRODUCT_ID)));
            ownerUrls.add(addApiPrefix(addMatToProPrefix(STORE)));
            ownerUrls.add(addApiPrefix(addMatToProPrefix(UPDATE)));
            ownerUrls.add(addApiPrefix(addMatToProPrefix(DELETE)));
            ownerUrls.add(addApiPrefix(addMatToProPrefix(SEARCH)));
            
            roleUrls.put(HardRoles.OWNER.toString(), ownerUrls);
            
            // Counter staff
            List<String> counterStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.COUNTER_STAFF.toString(), counterStaffUrls);
            
            // Serving staff
            List<String> servingStaffUrls = new ArrayList<>();
            
            roleUrls.put(HardRoles.SERVING_STAFF.toString(), servingStaffUrls);
            
            return roleUrls;
        }
    }
    
    public List<AuthUrl> getAllAuthUrl() {
        ArrayList<AuthUrl> result = new ArrayList<>();
        result.add(new Employee());
        result.add(new Role());

        return result;
    }
}


