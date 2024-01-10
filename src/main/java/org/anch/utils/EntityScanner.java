package org.anch.utils;

import jakarta.persistence.Entity;
import org.reflections.Reflections;

import java.util.Set;

public class EntityScanner {
    public static Set<Class<?>> getEntities() {
        Reflections reflections = new Reflections("org.anch.domain");
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

}
