package com.naukri.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.naukri.base.BaseTest;

public class Hooks extends BaseTest {

    @Before
    public void setUp() {
        //initializeDriver();
        // Additional setup actions can be added here
    }

    @After
    public void tearDown() {
        //closeDriver();
        // Additional teardown actions can be added here
    }
}