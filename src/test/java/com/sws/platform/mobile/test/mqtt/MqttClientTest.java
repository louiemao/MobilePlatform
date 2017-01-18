package com.sws.platform.mobile.test.mqtt;

import com.sws.platform.mobile.common.mqtt.MqttClient;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Louie on 2016/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class MqttClientTest {
    public static void main(String[] args) {
        MqttClient.getInstance().publish("test", "test", "this is a test", null, null, null);
        while (true) {

        }
    }
}
