package org.vaccom.vcmgt.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

public final class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        final YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        final Resource resource = encodedResource.getResource();
        if (resource.getFilename() == null) throw new IOException("Invalid resource");
        factory.setResources(resource);
        if (factory.getObject() == null) throw new IOException("Invalid properties");
        return new PropertiesPropertySource(resource.getFilename(), factory.getObject());
    }
}