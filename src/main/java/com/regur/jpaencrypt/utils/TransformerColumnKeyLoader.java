package com.regur.jpaencrypt.utils;

import com.regur.jpaencrypt.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author leeseungmin on 2019-09-23
 */
@Component(value = "transformerColumnKeyLoader")
public class TransformerColumnKeyLoader {
    public static final String KEY_ANNOTATION_PROPERTY = "${encryption.key}";

    @PostConstruct
    public void postConstruct() {
        setKey(User.class, "name");
        setKey(User.class, "hpNo");
    }

    private void setKey(Class<?> clazz, String columnName) {
        try {
            Field field = clazz.getDeclaredField(columnName);
            Annotation[] columnTransformer = field.getDeclaredAnnotations();
            for (int i = 0; i < columnTransformer.length; i++) {
                updateAnnotationValue(columnTransformer[i], "read");
                updateAnnotationValue(columnTransformer[i], "write");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Encryption key cannot be loaded into %s,%s", clazz.getName(), columnName));
        }
    }

    @SuppressWarnings("unchecked")
    private void updateAnnotationValue(Annotation annotation, String annotationProperty) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field memberValueField;
        try {
            memberValueField = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }

        memberValueField.setAccessible(true);
        Map<String, Object> memberValues;

        try {
            memberValues = (Map<String, Object>) memberValueField.get(handler);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }

        Object oldValue = memberValues.get(annotationProperty);
        if (oldValue == null || oldValue.getClass() != String.class) {
            throw new IllegalArgumentException(String.format("Annotation value should be string. Current va;ie os pf type : %s", oldValue.getClass().getName()));
        }

        String oldValueString = oldValue.toString();
        if (!oldValueString.contains(TransformerColumnKeyLoader.KEY_ANNOTATION_PROPERTY)) {
            throw new IllegalArgumentException(String.format("Annotation value should be contain %s. Current value is : %s", TransformerColumnKeyLoader.KEY_ANNOTATION_PROPERTY, GlobalValue.SECRET_KEY));
        }

        String newValueString = oldValueString.replace(TransformerColumnKeyLoader.KEY_ANNOTATION_PROPERTY, GlobalValue.SECRET_KEY);

        System.out.println("newValueString" + newValueString);

        memberValues.put(annotationProperty, newValueString);
    }
}
