package com.github.alexlight44.restaurantvoting.util;


import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

@UtilityClass
public final class HibernateProxyHelper {

    public static Class getClassWithoutInitializingProxy(Object object) {
        return (object instanceof HibernateProxy proxy) ?
                proxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
    }
}
