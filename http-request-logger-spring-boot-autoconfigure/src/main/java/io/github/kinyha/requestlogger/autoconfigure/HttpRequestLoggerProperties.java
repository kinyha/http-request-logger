package io.github.kinyha.requestlogger.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration properties for HTTP Request Logger
 */
@ConfigurationProperties(prefix = "http.request.logger")
public class HttpRequestLoggerProperties {

    /**
     * Enable/disable request logging
     */
    private boolean enabled = true;

    /**
     * Include request headers in logs
     */
    private boolean includeHeaders = false;

    /**
     * Include request body in logs
     */
    private boolean includePayload = false;

    /**
     * Include response body in logs
     */
    private boolean includeResponse = false;

    /**
     * Maximum payload length to log (to avoid huge logs)
     */
    private int maxPayloadLength = 1000;

    /**
     * URL patterns to exclude from logging
     */
    private List<String> excludePatterns = new ArrayList<>();

    /**
     * Log level (INFO, DEBUG, TRACE)
     */
    private String logLevel = "INFO";

    // Getters and Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isIncludeHeaders() {
        return includeHeaders;
    }

    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    public boolean isIncludePayload() {
        return includePayload;
    }

    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    public boolean isIncludeResponse() {
        return includeResponse;
    }

    public void setIncludeResponse(boolean includeResponse) {
        this.includeResponse = includeResponse;
    }

    public int getMaxPayloadLength() {
        return maxPayloadLength;
    }

    public void setMaxPayloadLength(int maxPayloadLength) {
        this.maxPayloadLength = maxPayloadLength;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Show ASCII art for GET requests
     */
    private boolean showAsciiArt = true;

    // Геттер и сеттер
    public boolean isShowAsciiArt() {
        return showAsciiArt;
    }

    public void setShowAsciiArt(boolean showAsciiArt) {
        this.showAsciiArt = showAsciiArt;
    }
}