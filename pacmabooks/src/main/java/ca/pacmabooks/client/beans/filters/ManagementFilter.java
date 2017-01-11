
package ca.pacmabooks.client.beans.filters;

/**
 * Used to restrict access to the management site
 * only if the user is logged in and is a manger.
 * @author Michael McMahon
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;

import ca.pacmabooks.client.backingbeans.AuthenticationBackingBean;
import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 *
 * @author itcuties http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 *
 * Changed to annotation from web.xml
 *
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/management/*"})
public class ManagementFilter implements Filter {

    private ServletContext context;
    @Inject
    private AuthenticationBackingBean loginBean;
    

    
    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     *
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        if (loginBean == null || !loginBean.isManager()) {
           
            String contextPath = ((HttpServletRequest) request)
                    .getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath
                    + "/landing.xhtml");
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        context = config.getServletContext();
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }

}