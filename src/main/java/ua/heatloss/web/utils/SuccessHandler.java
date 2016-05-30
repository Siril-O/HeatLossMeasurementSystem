package ua.heatloss.web.utils;


import ua.heatloss.domain.user.Role;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SuccessHandler implements AuthenticationSuccessHandler {


    private final static String EMPLOYEE_URL = "";
    private final static String CUSTOMER_URL = "";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication
                .getAuthorities());

        if (roles.contains(Role.EMPLOYEE.toString())) {
            httpServletResponse.sendRedirect(httpServletResponse
                    .encodeRedirectURL(EMPLOYEE_URL));
            return;
        }
        if (roles.contains(Role.CUSTOMER.toString())) {
            httpServletResponse.sendRedirect(httpServletResponse
                    .encodeRedirectURL(CUSTOMER_URL));
            return;
        }
    }
}
