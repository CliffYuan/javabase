package com.cliffyuan.jmx;

import java.util.Map;

/**
 * Created by xiaoniudu on 15-6-27.
 */
public class Echo implements EchoMBean {

    @Override
    public String echo(String echo) {
        return "JMX" + echo;
    }

    @Override
    public Map<String, String> map() {
        return null;
    }
}
