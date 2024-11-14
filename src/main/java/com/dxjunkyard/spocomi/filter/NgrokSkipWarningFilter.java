package com.dxjunkyard.spocomi.filter;

import com.dxjunkyard.spocomi.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*
 *
 */
@Component
public class NgrokSkipWarningFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(NgrokSkipWarningFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("filter start");

        // ヘッダーにngrok-skip-browser-warningを追加
        response.setHeader("ngrok-skip-browser-warning", "true");
        filterChain.doFilter(request, response);
        logger.info("filter end");

    }
}

