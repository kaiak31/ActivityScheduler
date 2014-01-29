package com.kaibot.springrest.test;


import junit.framework.TestCase;

public class TestApplication extends TestCase {

    @Test
    public static testApp(){
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

    }

}