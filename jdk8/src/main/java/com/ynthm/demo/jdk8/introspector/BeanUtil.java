package com.ynthm.demo.jdk8.introspector;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
public class BeanUtil {

  public static <T> void setPropertyIntroSpector(
      Class<T> clazz, T bean, String propertyName, Object value) throws Exception {
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
    PropertyDescriptor[] proDescriptors = beanInfo.getPropertyDescriptors();
    if (proDescriptors != null) {
      for (PropertyDescriptor propDesc : proDescriptors) {
        if (propDesc.getName().equals(propertyName)) {
          Method methodSetUserName = propDesc.getWriteMethod();
          methodSetUserName.invoke(bean, value);
          break;
        }
      }
    }
  }

  public static <T> Object getPropertyIntroSpector(Class<T> clazz, T bean, String propertyName)
      throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

    PropertyDescriptor[] proDescriptors = beanInfo.getPropertyDescriptors();
    if (proDescriptors != null) {
      for (PropertyDescriptor propDesc : proDescriptors) {
        if (propDesc.getName().equals(propertyName)) {
          Method methodGetUserName = propDesc.getReadMethod();
          return methodGetUserName.invoke(bean);
        }
      }
    }

    return null;
  }

  public static <T> void setProperty(Class<T> clazz, T bean, String propertyName, Object value)
      throws Exception {
    PropertyDescriptor propDesc = new PropertyDescriptor(propertyName, clazz);
    Method methodSetUserName = propDesc.getWriteMethod();
    methodSetUserName.invoke(bean, value);
  }

  public static <T> Object getProperty(Class<T> clazz, T bean, String propertyName)
      throws Exception {
    PropertyDescriptor proDescriptor = new PropertyDescriptor(propertyName, clazz);
    Method methodGetUserName = proDescriptor.getReadMethod();
    return methodGetUserName.invoke(bean);
  }

  public static void main(String[] args) throws Exception {
    User user = new User();
    setProperty(User.class, user, "name", "Ethan Wang");
    setPropertyIntroSpector(User.class, user, "age", 18);
    System.out.println(getPropertyIntroSpector(User.class, user, "name"));
    System.out.println(getProperty(User.class, user, "age"));
  }
}
