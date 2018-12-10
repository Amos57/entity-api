package com.amos.entityapi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 
 * 
 * данная аннотация позволяет игнорировать
 * любое поле в классе.
 * наличие над полем дает преймущество перед остальными 
 * аннотациями из пакета com.amos.annotations поле с данной
 * аннотацией будет проигнориванно
 * 
 * @author Amos57
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignor {
}
