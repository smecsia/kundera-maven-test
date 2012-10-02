package me.smecsia.test.kundera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Service allowing to autowire fields into the instance of a bean
 *
 * @author: Ilya Sadykov
 * Date: 19.09.12
 * Time: 14:04
 */
@Service
public class AutowiringSupportService extends BasicService {

    private ApplicationContext context;

    @Autowired
    public AutowiringSupportService(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Autowire all fields into the bean
     *
     * @param bean instance
     */
    public void autowireFields(Object bean) {
        AutowiredAnnotationBeanPostProcessor bpp = new AutowiredAnnotationBeanPostProcessor();
        bpp.setBeanFactory(context.getAutowireCapableBeanFactory());
        bpp.processInjection(bean);
    }
}
