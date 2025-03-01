package com.ewm.card.ewmhealthchecktool.constant;

public final class Constants {
    private Constants() {
        throw new UnsupportedOperationException();
    }

    //<editor-fold desc="Environment">
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    //</editor-fold>

    //<editor-fold desc="Email">
    public static final String PROP_MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String PROP_MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String PROP_MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String PROP_MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";

    public static final boolean HTML_TEXT = true;
    //</editor-fold>

    //<editor-fold desc="Thymeleaf">
    public static final String THYMELEAF_TEMPLATE_PREFIX = "mail/";
    public static final String THYMELEAF_TEMPLATE_SUFFIX = ".html";
    //</editor-fold>
}
