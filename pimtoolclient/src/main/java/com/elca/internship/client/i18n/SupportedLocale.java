package com.elca.internship.client.i18n;

import java.util.Locale;

public enum SupportedLocale {
    ENGLISH(Locale.ENGLISH), FRANCE(Locale.FRANCE);

    public static final SupportedLocale DEFAULT_LOCALE = ENGLISH;

    private final Locale locale;

    SupportedLocale(final Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
