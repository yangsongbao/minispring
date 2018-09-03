package pers.minispring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.aop.MethodMatcher;
import pers.minispring.aop.aspectj.AspectJExpressionPointcut;
import pers.minispring.service.v5.PetStoreService;

import java.lang.reflect.Method;

public class PointcutTest {

    @Test
    public void testPointcut() throws Exception {

        String expression = "execution(* pers.minispring.service.v5.*.placeOrder(..))";

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        pointcut.setExpression(expression);

        MethodMatcher mm = pointcut.getMethodMatcher();

        {
            Class<?> targetClass = PetStoreService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = PetStoreService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }
    }
}
