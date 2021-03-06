package ru.javaops.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javaops.ApplicationAbstractTest;

/**
 * GKislin
 * 25.02.2016
 */
@ActiveProfiles("prod")
public class SubscriptionServiceTest extends ApplicationAbstractTest {

    public static final String EMAIL = "gkislin@yandex.ru";

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void testActivationKey() throws Exception {
        String key = subscriptionService.generateActivationKey(EMAIL);
        System.out.println("+++++++++");
        System.out.println(key);
        System.out.println("+++++++++");
        subscriptionService.checkActivationKey(EMAIL, key);
    }
}