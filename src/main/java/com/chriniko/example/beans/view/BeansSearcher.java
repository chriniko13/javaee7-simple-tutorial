package com.chriniko.example.beans.view;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class BeansSearcher implements Serializable {

    @Inject
    BeanManager beanManager;

    public List<String> beans() {

        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {
        });

        return beans
                .stream()
                .map(bean -> " ###" + bean.getBeanClass().getName())
                .filter(beanName -> beanName.contains("com.chriniko.example"))
                .collect(Collectors.toList());
    }


}
