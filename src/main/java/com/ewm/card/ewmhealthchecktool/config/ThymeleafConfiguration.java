package com.ewm.card.ewmhealthchecktool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

import static com.ewm.card.ewmhealthchecktool.constant.Constants.THYMELEAF_TEMPLATE_PREFIX;
import static com.ewm.card.ewmhealthchecktool.constant.Constants.THYMELEAF_TEMPLATE_SUFFIX;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix(THYMELEAF_TEMPLATE_PREFIX);
        emailTemplateResolver.setSuffix(THYMELEAF_TEMPLATE_SUFFIX);
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        emailTemplateResolver.setOrder(1);

        return emailTemplateResolver;
    }
}
