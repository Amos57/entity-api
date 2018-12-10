package com.amos.entityapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {
	
	String table();
	String column();
	Cascade caskade() default Cascade.NON;
	FetchType fethe() default FetchType.EAGER;

}
