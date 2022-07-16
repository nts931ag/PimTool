package com.elca.internship.client.i18n;

import ch.qos.logback.core.CoreConstants;

import java.util.Locale;

public enum SupportedLocale {
    ENGLISH(Locale.ENGLISH), SPANISH(new Locale("es"));

    public static final SupportedLocale DEFAULT_LOCALE = ENGLISH;

    private final Locale locale;

    SupportedLocale(final Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
