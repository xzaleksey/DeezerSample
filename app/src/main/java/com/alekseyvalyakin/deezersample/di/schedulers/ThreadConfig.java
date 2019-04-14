package com.alekseyvalyakin.deezersample.di.schedulers;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Dagger annotation
 * Custom scope for Parent fragment
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadConfig {
    TYPE value();

    enum TYPE {
        UI,
        IO,
        COMPUTATATION
    }
}