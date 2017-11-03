package com.cmos.edccommon.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;

/**
 * Created by Administrator on 2017/7/20.
 */
public final class BeanUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static Logger logger = LoggerFactory.getUtilLog(BeanUtil.class);

    public static Date parse(String date) {
        String method = "parse";
        try {
            return isEmpty(date) ? null : dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error(method, "convert String 2 Date Error.", date, e);
        }
        return null;
    }

    public static String format(Date date) {
        return date == null ? "" : dateFormat.format(date);
    }

    public static boolean isEmpty(String value) {
        return (value == null) || ("".equals(value.trim()));
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static Object getValueFromField(Field field, String name, String value) {
        Object object = value;
        String type = field.getType().getSimpleName();

        if ((isEmpty(value)) && ("short,int,Integer,long,double,float".contains(type))) {
            value = "0";
        }

        if ("short".equalsIgnoreCase(type))
            object = Short.valueOf(value);
        else if (("char".equals(type)) || ("Character".equals(type))) {
            if (!isEmpty(value))
                object = Character.valueOf(value.charAt(0));
        } else if (("int".equals(type)) || ("Integer".equals(type)))
            object = Integer.valueOf(value);
        else if ("long".equalsIgnoreCase(type))
            object = Long.valueOf(value);
        else if ("byte".equalsIgnoreCase(type))
            object = Byte.valueOf(value);
        else if ("float".equalsIgnoreCase(type))
            object = Float.valueOf(value);
        else if ("double".equalsIgnoreCase(type))
            object = Double.valueOf(value);
        else if ("boolean".equalsIgnoreCase(type))
            object = Boolean.valueOf(value);
        else if ("Date".equals(type))
            object = parse(value);
        else if ("byte[]".equalsIgnoreCase(type)) {
            object = ConvertUtil.string2Bytes(value);
        }
        return object;
    }

    public static Object getValueFromField(Object object, Field field) {
        try {
            Object value = field.get(object);
            String type = field.getType().getSimpleName();
            if ("short".equals(type))
                value = Short.valueOf(field.getShort(object));
            else if ("char".equals(type))
                value = Character.valueOf(field.getChar(object));
            else if ("int".equals(type))
                value = Integer.valueOf(field.getInt(object));
            else if ("long".equals(type))
                value = Long.valueOf(field.getLong(object));
            else if ("byte".equals(type))
                value = Byte.valueOf(field.getByte(object));
            else if ("float".equals(type))
                value = Float.valueOf(field.getFloat(object));
            else if ("double".equals(type))
                value = Double.valueOf(field.getDouble(object));
            else if ("boolean".equals(type))
                value = Boolean.valueOf(field.getBoolean(object));
            else if ("Date".equals(type))
                value = format((Date) value);
            else if ("byte[]".equalsIgnoreCase(type)) {
                if (value != null)
                    value = ConvertUtil.bytes2String((byte[]) value);
            } else if ("String".equalsIgnoreCase(type)) {
                if (value == null)
                    value = "";
            } else {
                value = field.get(object);
            }
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Field[] getSuperFields(Class<?> subCls, List<Field> subFields) {
        Class superClass = subCls.getSuperclass();
        subFields = new ArrayList(subFields);
        if ((superClass != null) && (superClass != Object.class)) {
            Field[] superFields = superClass.getDeclaredFields();
            subFields.addAll(Arrays.asList(superFields));
            if (superClass.getSuperclass() != Object.class) {
                getSuperFields(superClass, subFields);
            }
        }
        return (Field[]) subFields.toArray(new Field[subFields.size()]);
    }

    public static <T> T convertMap2Bean(Map<String, String> bean, Class<T> clz) {
        if (bean == null) {
            return null;
        }
        try {
            Object t = clz.newInstance();
            Field[] fields = t.getClass().getDeclaredFields();
            fields = getSuperFields(clz, Arrays.asList(fields));
            for (Field field : fields) {
                String key = field.getName();
                String value = (String) bean.get(key);
                if ((value == null) || ("null".equals(value)))
                    continue;
                boolean visiable = field.isAccessible();
                field.setAccessible(Boolean.TRUE.booleanValue());
                Object object = getValueFromField(field, key, value);
                field.set(t, object);
                field.setAccessible(visiable);
            }
            return (T) t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/*    public static Map<String, String> convertBean2Map(Object object) {
        if (object == null) {
            return null;
        }
        Map bean = new HashMap();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            fields = getSuperFields(object.getClass(), Arrays.asList(fields));
            for (Field field : fields) {
                String key = field.getName();

                if ((!"serialVersionUID".equals(key)) && (!Modifier.isStatic(field.getModifiers()))) {
                    boolean visible = field.isAccessible();
                    field.setAccessible(Boolean.TRUE.booleanValue());
                    String value = (new StringBuilder()).append(getValueFromField(object, field)).toString();
                    field.setAccessible(visible);
                    bean.put(key, value);
                }
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    public static List<Map<String, String>> convertBeans2List(Object[] objects){
        if (objects == null) {
            return null;
        }
        List beans = new ArrayList();

        int j = objects.length;
        for (int i = 0; i < j; i++) {
            Object object = objects[i];
            Map bean = convertBean(object);
            if ((bean != null) && (bean.size() != 0)) {
                beans.add(bean);
            }
        }
        return beans;
    }

    public static Map convertBean(Object bean) {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            logger.error("转bean出错",e);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        try {
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    }
                }
            }
        }catch (IllegalAccessException e){
            logger.error("转bean出错",e);
        }catch (InvocationTargetException e){
            logger.error("转bean出错",e);
        }
        return returnMap;
    }
    
/*    public static List<Map<String, String>> convertBeans2List(Object[] objects) {
        if (objects == null) {
            return null;
        }
        List beans = new ArrayList();
        Object[] arrayOfObject = objects;
        int j = objects.length;
        for (int i = 0; i < j; i++) {
            Object object = arrayOfObject[i];
            Map bean = convertBean2Map(object);
            if ((bean != null) && (bean.size() != 0)) {
                beans.add(bean);
            }
        }
        return beans;
    }
*/
    public static void main(String[] args) {
        List entities = new ArrayList();
//        List result = convertBeans2List(new Object[]{entities});
//        System.out.println(result.size());
    }
}
