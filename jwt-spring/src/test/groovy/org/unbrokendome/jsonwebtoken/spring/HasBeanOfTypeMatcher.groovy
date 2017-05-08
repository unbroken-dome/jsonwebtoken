package org.unbrokendome.jsonwebtoken.spring

import org.hamcrest.Description
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.springframework.beans.factory.ListableBeanFactory


class HasBeanOfTypeMatcher extends TypeSafeDiagnosingMatcher<ListableBeanFactory> {

    private final Class<?> beanClass

    HasBeanOfTypeMatcher(Class<?> beanClass) {
        this.beanClass = beanClass
    }

    @Override
    protected boolean matchesSafely(ListableBeanFactory beanFactory, Description mismatchDescription) {
        def beans = beanFactory.getBeansOfType(beanClass)
        if (beans.isEmpty()) {
            mismatchDescription.appendText("no bean of type ")
                    .appendText(beanClass.name)
                    .appendText(" present")
            return false
        } else {
            return true
        }
    }


    @Override
    void describeTo(Description description) {
        description
                .appendText("has bean of type ")
                .appendText(beanClass.name)
    }
}
