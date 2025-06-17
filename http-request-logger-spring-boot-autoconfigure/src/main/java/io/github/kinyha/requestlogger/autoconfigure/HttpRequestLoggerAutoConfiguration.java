package io.github.kinyha.requestlogger.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(Filter.class)
@ConditionalOnProperty(
        prefix = "http.request.logger",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties(HttpRequestLoggerProperties.class)
public class HttpRequestLoggerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestLoggerAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<HttpRequestLoggingFilter> httpRequestLoggingFilter(
            HttpRequestLoggerProperties properties) {

        log.info("=== CREATING HTTP REQUEST LOGGING FILTER ==="); // Добавь это
        log.info("Properties: enabled={}, includePayload={}", properties.isEnabled(), properties.isIncludePayload());

        FilterRegistrationBean<HttpRequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpRequestLoggingFilter(properties));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setName("httpRequestLoggingFilter");

        return registrationBean;
    }
}
