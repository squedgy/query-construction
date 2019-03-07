package com.dfaris.query.construction.preprocess;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.dfaris.query.construction.where.AbstractWhereBuilder;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@ChildOf(parentClass = AbstractWhereBuilder.class)
public @interface HasBindableVersion { }
