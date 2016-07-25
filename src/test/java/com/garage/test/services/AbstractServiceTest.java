package com.garage.test.services;

import com.garage.config.JpaConfig;
import com.garage.config.WebConfig;
import com.garage.test.conf.TestApplicationConfig;
import com.garage.test.utils.DBUnitHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Ruslan Yaniuk
 * @date September 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebConfig.class,
        TestApplicationConfig.class,
        JpaConfig.class
})
public abstract class AbstractServiceTest {

    @Autowired
    protected DBUnitHelper dbUnit;
}
