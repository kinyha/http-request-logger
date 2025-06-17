package io.github.kinyha.requestlogger.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class HttpRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestLoggingFilter.class);
    private final HttpRequestLoggerProperties properties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // ASCII Art константа
    private static final String GET_SUCCESS_ART = """

    ██                     │       - Senior Developer        │                   ██
            
    """;

    public HttpRequestLoggingFilter(HttpRequestLoggerProperties properties) {
        this.properties = properties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return properties.getExcludePatterns().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!properties.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper, responseWrapper, System.currentTimeMillis() - startTime);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request,
                            ContentCachingResponseWrapper response,
                            long duration) {

        // Проверяем нужно ли показать ASCII арт для GET
        if (properties.isShowAsciiArt() &&
                "GET".equals(request.getMethod()) &&
                response.getStatus() >= 200 && response.getStatus() < 300) {

            log.info(GET_SUCCESS_ART);
        }

        // Обычное логирование
        StringBuilder message = new StringBuilder();
        message.append("HTTP Request: ")
                .append(request.getMethod()).append(" ")
                .append(request.getRequestURI());

        if (request.getQueryString() != null) {
            message.append("?").append(request.getQueryString());
        }

        message.append(" | Status: ").append(response.getStatus())
                .append(" | Duration: ").append(duration).append("ms");

        if (properties.isIncludeHeaders()) {
            message.append(" | Headers: {");
            Collections.list(request.getHeaderNames()).forEach(headerName ->
                    message.append(headerName).append(": ")
                            .append(request.getHeader(headerName)).append(", ")
            );
            message.append("}");
        }

        if (properties.isIncludePayload() && request.getContentLength() > 0) {
            byte[] content = request.getContentAsByteArray();
            if (content.length > 0) {
                String payload = new String(content, StandardCharsets.UTF_8);
                if (payload.length() > properties.getMaxPayloadLength()) {
                    payload = payload.substring(0, properties.getMaxPayloadLength()) + "...";
                }
                message.append(" | Request Body: ").append(payload);
            }
        }

        if (properties.isIncludeResponse()) {
            byte[] content = response.getContentAsByteArray();
            if (content.length > 0) {
                String responseBody = new String(content, StandardCharsets.UTF_8);
                if (responseBody.length() > properties.getMaxPayloadLength()) {
                    responseBody = responseBody.substring(0, properties.getMaxPayloadLength()) + "...";
                }
                message.append(" | Response Body: ").append(responseBody);
            }
        }

        // Логирование в зависимости от уровня
        switch (properties.getLogLevel().toUpperCase()) {
            case "DEBUG":
                log.debug(message.toString());
                break;
            case "TRACE":
                log.trace(message.toString());
                break;
            default:
                log.info(message.toString());
        }
    }
}
