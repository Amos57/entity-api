package com.amos.entityapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
	String table();
	String column();
	Cascade caskade() default Cascade.NON;
	FetchType fethe() default FetchType.EAGER;
}
