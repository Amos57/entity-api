package com.amos.entityapi.annotations;

/**
 * 
 * 
 * <p>FetchType.EAGER — загружать коллекцию дочерних объектов сразу же,
 *  при загрузке родительских объектов. 
 * <p>FetchType.LAZY — загружать коллекцию дочерних объектов при первом обращении к ней
 *  (вызове get) — так называемая отложенная загрузка.
 * 
 * @author Amos57
 *
 */
public enum FetchType {
	LAZY,EAGER 
}
