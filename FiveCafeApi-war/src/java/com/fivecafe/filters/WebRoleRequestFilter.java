/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.fivecafe.filters;

import com.fivecafe.entities.Employees;
import com.fivecafe.enums.RequestAttributeKeys;
import com.fivecafe.enums.TokenNames;
import com.fivecafe.providers.JwtProvider;
import com.fivecafe.providers.WebAuthUrl;
import com.fivecafe.providers.WebUrlProvider;
import com.fivecafe.services.UserTokenService;
import com.fivecafe.session_beans.EmployeesFacadeLocal;
import com.fivecafe.supports.CookieSupport;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebFilter(filterName = "WebRoleRequestFilter", urlPatterns = {"/*"})
public class WebRoleRequestFilter implements Filter {

    EmployeesFacadeLocal employeesFacade = lookupEmployeesFacadeLocal();
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    private final UserTokenService userTokenService = new UserTokenService();
    
    public WebRoleRequestFilter() {
    }
    
    private boolean isMatchPath(String path, List<String> urlDefineds) {
        // path: /api/order/cancel/3
        // urlDefined: /api/order/cancel/{id}
        
        for (String urlDef : urlDefineds) {
            String[] pathSections = path.split("/");
            String[] urlDefSections = urlDef.split("/");

            if (pathSections.length == urlDefSections.length) {
                boolean isContain = true;

                for (int i = 0; i < urlDefSections.length; i++) {
                    String sectionOfPath = pathSections[i];
                    String sectionOfUrlDef = urlDefSections[i];

                    if (sectionOfUrlDef.contains("{") && sectionOfUrlDef.contains("}")) {
                        // Next
                    } else if (sectionOfPath.equals(sectionOfUrlDef)) {
                        // Next
                    } else {
                        isContain = false;
                        break;
                    }
                }
                if (isContain) return true;
            }
        }
        
        return false;
    }
    
    private List<String> getAllUrls() {
        HashMap<String, List<String>> roleUrls = getRoleUrls();
        List<String> allUrls = new ArrayList<>();
        
        for (Map.Entry<String, List<String>> entry : roleUrls.entrySet()) {
            allUrls.addAll(roleUrls.get(entry.getKey()));
        }
        
        return allUrls;
    }
    
    private HashMap<String, List<String>> getRoleUrls() {
        WebUrlProvider webUrlProvider = new WebUrlProvider();
        
        List<WebAuthUrl> webAuthUrls = webUrlProvider.getAllAuthUrl();
        
        HashMap<String, List<String>> mergedMap = new HashMap<>();
        
        for (WebAuthUrl webAuthUrl : webAuthUrls) {
            for (Map.Entry<String, List<String>> entry : webAuthUrl.roleUrls().entrySet()) {
                String key = entry.getKey();
                List<String> urlsValue = entry.getValue();
                
                if (mergedMap.containsKey(key)) {
                    mergedMap.get(key).addAll(urlsValue);
                } else {
                    mergedMap.put(key, urlsValue);
                }
            }
        }
        
        return mergedMap;
    }
    
    private boolean isMatchRole(String role, String path, HashMap<String, List<String>> roleUrls) {
        for (Map.Entry<String, List<String>> entry : roleUrls.entrySet()) {
            List<String> urls = entry.getValue();
            
            if (urls.contains(path) && entry.getKey().equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("WebRoleRequestFilter:doFilter()");
        }
        
        Throwable problem = null;
        try {
            // ->> Logic code at here ------------------------
            
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            
            String loginUrl = httpRequest.getContextPath() + "/login";
            
            // Get request url
            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            
            HashMap<String, List<String>> roleUrls = getRoleUrls();
            
            List<String> allUrls = getAllUrls();
            
            // Kiểm tra xem các urls có khớp với path
            if (isMatchPath(path, allUrls)) {
                
                // Lấy access token
                Cookie accessTokenCookie = CookieSupport.getSpecificCookie(httpRequest, TokenNames.ACCESS_TOKEN.toString());
                if (accessTokenCookie != null) {
                    String accessToken = accessTokenCookie.getValue();
                    
                    // Kiểm tra accessToken
                    Map<String, Object> verifyATResult = userTokenService.verifyAccessToken(accessToken);
                    // accessToken hợp lệ
                    if ((boolean) verifyATResult.get(JwtProvider.VERIFY_RESULT_KEY)) {
                        String userID = userTokenService.getDataOfAT(accessToken);
                        Employees emp = employeesFacade.find(Integer.parseInt(userID));
                        
                        // Khi role khớp với khai báo
                        if (isMatchRole(emp.getRoleID().getRoleID(), path, roleUrls)) {
                            httpRequest.setAttribute(RequestAttributeKeys.USER_ID.toString(), userID);
                            chain.doFilter(request, response);
                            return;
                        } else {
                            httpResponse.sendRedirect(loginUrl);
                        }
                    } else {
                        // Kiểm tra nếu accessToken hết hạn
                        if (verifyATResult.get(JwtProvider.VERIFY_ERROR_KEY).equals(JwtProvider.TOKEN_WAS_EXPIRED)) {
                            Cookie refreshTokenCookie = CookieSupport.getSpecificCookie(httpRequest, TokenNames.REFRESH_TOKEN.toString());
                            // Kiểm tra refreshToken not null
                            if (refreshTokenCookie != null) {
                                String refreshToken = refreshTokenCookie.getValue();
                                
                                Map<String, Object> verifyRTResult = userTokenService.verifyRefreshToken(refreshToken);
                                // Nếu refresh token hợp lệ
                                if ((boolean) verifyRTResult.get(JwtProvider.VERIFY_RESULT_KEY)) {
                                    String userId = userTokenService.getDataOfRT(refreshToken);
                                    if (userId != null) {
                                        // Reset Access token
                                        String newAccessToken = userTokenService.createAccessToken(userId);
                                        Cookie newAccessTokenCookie = new Cookie(TokenNames.ACCESS_TOKEN.toString(), newAccessToken);
                                        newAccessTokenCookie.setMaxAge(60 * 60 * 24 * 365 * 60);
                                        newAccessTokenCookie.setHttpOnly(true);
                                        newAccessTokenCookie.setPath("/");
                                        
                                        httpResponse.addCookie(newAccessTokenCookie);
                                        
                                        Employees emp = employeesFacade.find(Integer.parseInt(userId));
                        
                                        // Khi role khớp với khai báo
                                        if (isMatchRole(emp.getRoleID().getRoleID(), path, roleUrls)) {
                                            httpRequest.setAttribute(RequestAttributeKeys.USER_ID.toString(), userId);
                                            chain.doFilter(request, response);
                                            return;
                                        } else {
                                            httpResponse.sendRedirect(loginUrl);
                                        }
                                    }
                                }
                            }
                        }
                        
                        httpResponse.sendRedirect(loginUrl);
                    }
                } else {
                    // If cookie is null
                    httpResponse.sendRedirect(loginUrl);
                }
            } else {
                chain.doFilter(request, response);
            }
            
            // ->> END Logic code
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }
        
        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("WebRoleRequestFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("WebRoleRequestFilter()");
        }
        StringBuffer sb = new StringBuffer("WebRoleRequestFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }

    private EmployeesFacadeLocal lookupEmployeesFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmployeesFacadeLocal) c.lookup("java:global/FiveCafeApi/FiveCafeApi-ejb/EmployeesFacade!com.fivecafe.session_beans.EmployeesFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
