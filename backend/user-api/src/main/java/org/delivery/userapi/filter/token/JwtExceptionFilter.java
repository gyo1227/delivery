package org.delivery.userapi.filter.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.delivery.common.api.ApiResponse;
import org.delivery.common.error.ErrorCodeIfs;
import org.delivery.common.exception.ApiException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            errorResponse(response, e.getErrorCodeIfs());
        }
    }

    private void errorResponse(HttpServletResponse response, ErrorCodeIfs errorCode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var apiResponse = ApiResponse.Error(errorCode, errorCode.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatusCode());
        response.getWriter().write(mapper.writeValueAsString(apiResponse));

    }
}
