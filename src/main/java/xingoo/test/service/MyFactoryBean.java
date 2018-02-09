package xingoo.test.service;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<AStatic> {

    @Override
    public AStatic getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
