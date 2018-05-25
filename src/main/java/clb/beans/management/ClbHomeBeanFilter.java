package clb.beans.management;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class ClbHomeBeanFilter implements Filter {
     
    
    private final static String HOME_PAGE = "index.xhtml";
    private final static String REGISTER_COMPLETE_PAGE = "registerComplete.xhtml";
    
    public ClbHomeBeanFilter() {
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         try {
             
            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            HttpSession ses = req.getSession(false);
            
            ClbHomeLoginBean bean = ses != null ? (ClbHomeLoginBean)ses.getAttribute( "clbHomeLoginBean" ) : null;
   
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();
            if ( reqURI.indexOf("/" + HOME_PAGE) >= 0 || (bean != null &&  bean.getUserLoginPojo().getUsername() != null && 
                    !bean.getUserLoginPojo().getUsername().equals( "" ))
                    || reqURI.contains("javax.faces.resource") || reqURI.contains(REGISTER_COMPLETE_PAGE) )
                   chain.doFilter(request, response);
            else   // user didn't log in but asking for a page that is not allowed so take user to login page
                   res.sendRedirect(req.getContextPath() + "/pages/" + HOME_PAGE);  // Anonymous user. Redirect to login page
      }
     catch(Throwable t) {
         System.out.println( t.getMessage());
     }
    } //doFilter
 
    @Override
    public void destroy() {
         
    }
}