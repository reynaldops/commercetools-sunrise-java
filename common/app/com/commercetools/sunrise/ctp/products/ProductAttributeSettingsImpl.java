package com.commercetools.sunrise.ctp.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
final class ProductAttributeSettingsImpl implements ProductAttributeSettings {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAttributeSettings.class);

    private final List<String> displayedAttributes;
    private final List<String> primarySelectableAttributes;
    private final List<String> secondarySelectableAttributes;

    @Inject
    ProductAttributeSettingsImpl(final Configuration globalConfig) {
        this(globalConfig, "ctp.products.attributes");
    }

    ProductAttributeSettingsImpl(final Configuration globalConfig, final String configPath) {
        final Configuration config = globalConfig.getConfig(configPath);
        this.displayedAttributes = config.getStringList("displayed");
        this.primarySelectableAttributes = config.getStringList("primarySelectable");
        this.secondarySelectableAttributes = config.getStringList("secondarySelectable");
        LOGGER.debug("Initialized ProductAttributeSettings: displayed attributes {}, primary selectable attributes {}, secondary selectable attributes {}",
                displayedAttributes,
                primarySelectableAttributes,
                secondarySelectableAttributes);
    }

    @Override
    public List<String> displayed() {
        return displayedAttributes;
    }

    @Override
    public List<String> primarySelectable() {
        return primarySelectableAttributes;
    }

    @Override
    public List<String> secondarySelectable() {
        return secondarySelectableAttributes;
    }
}

