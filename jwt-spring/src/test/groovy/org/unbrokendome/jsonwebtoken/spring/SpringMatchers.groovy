package org.unbrokendome.jsonwebtoken.spring

import org.hamcrest.Matcher
import org.springframework.beans.factory.ListableBeanFactory


final class SpringMatchers {

    static Matcher<ListableBeanFactory> hasBeanOfType(Class<?> beanClass) {
        return new HasBeanOfTypeMatcher(beanClass)
    }
}
