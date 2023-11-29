package com.fivecafe.filters;

import com.fivecafe.enums.RequestAttributeKeys;
import com.fivecafe.enums.TokenNames;
import com.fivecafe.providers.JwtProvider;
import com.fivecafe.providers.WebAuthUrl;
import com.fivecafe.providers.WebUrlProvider;
import com.fivecafe.services.UserTokenService;
import com.fivecafe.supports.CookieSupport;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

@WebFilter(filterName = "WebRequestFilter", urlPatterns = {"/*"})
public class WebRequestFilter implements Filter {
    
    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    private final UserTokenService userTokenService = new UserTokenService();
    
    public WebRequestFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (DEBUG) {
            // log("ApiRequestFilter:DoBeforeProcessing");
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (DEBUG) {
            // log("ApiRequestFilter:DoAfterProcessing");
        }
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
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (DEBUG) {
//             log("ApiRequestFilter:doFilter()");
        }
        
        Throwable problem = null;
        try {
            // ->> Logic code at here ------------------------
            
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            
            String loginUrl = httpRequest.getContextPath() + "/login";
            
            // Get request url
            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
            
            
            
            // List signInUrls
            List<String> signInUrls = new ArrayList<>();
            
            WebUrlProvider urlProvider = new WebUrlProvider();
            for (WebAuthUrl authUrl : urlProvider.getAllAuthUrl()) {
                signInUrls.addAll(authUrl.signInUrls());
            }
            
            // Check list path is included in signInUrls
            if (isMatchPath(path, signInUrls)) {
//                System.out.println("->> ///////////////////////////////////// [ApiRequestFilter]");
//                System.out.println("PATH: " + path);
                
                // Take access token
                Cookie accessTokenCookie = CookieSupport.getSpecificCookie(httpRequest, TokenNames.ACCESS_TOKEN.toString());
                if (accessTokenCookie != null) {
                    String accessToken = accessTokenCookie.getValue();
//                    System.out.println("ACCESS TOKEN: " + accessToken);
                    
                    // Check accessToken
                    Map<String, Object> verifyATResult = userTokenService.verifyAccessToken(accessToken);
                    if ((boolean) verifyATResult.get(JwtProvider.VERIFY_RESULT_KEY)) {
//                        System.out.println("SUCCESSFULLY VERIFY ACCESS TOKEN");
                        httpRequest.setAttribute(RequestAttributeKeys.USER_ID.toString(), userTokenService.getDataOfAT(accessToken));
                        chain.doFilter(request, response);
                        return;
                    } else {
                        // Check if access token is expired
                        if (verifyATResult.get(JwtProvider.VERIFY_ERROR_KEY).equals(JwtProvider.TOKEN_WAS_EXPIRED)) {
//                            System.out.println("ACCESS TOKEN IS EXPIRED");
                            Cookie refreshTokenCookie = CookieSupport.getSpecificCookie(httpRequest, TokenNames.REFRESH_TOKEN.toString());
                            // Check refresh token cookie not null
                            if (refreshTokenCookie != null) {
                                String refreshToken = refreshTokenCookie.getValue();
//                                System.out.println("REFRESH TOKEN: " + refreshToken);
                                
                                Map<String, Object> verifyRTResult = userTokenService.verifyRefreshToken(refreshToken);
                                // If refresh token valid
                                if ((boolean) verifyRTResult.get(JwtProvider.VERIFY_RESULT_KEY)) {
                                    String userId = userTokenService.getDataOfRT(refreshToken);
//                                    System.out.println("REFRESH TOKEN IS VALID AND USER ID: " + userId);
                                    if (userId != null) {
                                        // Reset Access token
                                        String newAccessToken = userTokenService.createAccessToken(userId);
                                        Cookie newAccessTokenCookie = new Cookie(TokenNames.ACCESS_TOKEN.toString(), newAccessToken);
                                        newAccessTokenCookie.setMaxAge(60 * 60 * 24 * 365 * 60);
                                        newAccessTokenCookie.setHttpOnly(true);
                                        newAccessTokenCookie.setPath("/");
                                        
                                        httpResponse.addCookie(newAccessTokenCookie);
                                        httpRequest.setAttribute(RequestAttributeKeys.USER_ID.toString(), userId);
                                        
//                                        System.out.println("RESET ACCESS TOKEN AND PASS THROUGH FILTER");
                                        
                                        chain.doFilter(httpRequest, httpResponse);
                                        return;
                                    }
                                }
                            }
                        }
                        
//                        System.out.println("TOKEN WRONG AT SOME POINT");
                        httpResponse.sendRedirect(loginUrl);
                    }
                } else {
                    // If cookie is null
//                    System.out.println("ACCESS TOKEN COOKIE IS NULL");
                    httpResponse.sendRedirect(loginUrl);
                }
            } else {
                chain.doFilter(request, response);
            }
            
            // ->> END Logic code
            
        } catch (IOException | ServletException t) {
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
            if (DEBUG) {                
                log("ApiRequestFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ApiRequestFilter()");
        }
        StringBuffer sb = new StringBuffer("ApiRequestFilter(");
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
    
}
