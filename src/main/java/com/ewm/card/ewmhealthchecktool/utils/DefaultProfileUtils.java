package com.ewm.card.ewmhealthchecktool.utils;

import com.ewm.card.ewmhealthchecktool.constant.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.AbstractEnvironment;

import java.util.HashMap;
import java.util.Map;

public final class DefaultProfileUtils {
    private DefaultProfileUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Set a default to use when no profile is configured.
     *
     * @param app the Spring application
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, Constants.SPRING_PROFILE_DEVELOPMENT);

        app.setDefaultProperties(defProperties);
    }
}
