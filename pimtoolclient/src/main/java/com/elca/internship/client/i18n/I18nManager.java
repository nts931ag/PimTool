package com.elca.internship.client.i18n;

import com.elca.internship.client.exception.ErrorResponseKey;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableValue;

public interface I18nManager extends I18nBundleProvider{
    void setupLocale(final SupportedLocale locale);

    String text(I18nKey key, Object... arguments);
    String text(ErrorResponseKey key, Object... arguments);

    String text(SupportedLocale locale, I18nKey key, Object... arguments);
    String text(SupportedLocale locale, ErrorResponseKey key, Object... arguments);

    StringExpression textBinding(I18nKey key, ObservableValue<?>... arguments);

}
