package com.cliffyuan.jmx;

import java.util.Map;

/**
 * Created by xiaoniudu on 15-6-27.
 */
public interface EchoMBean {

    String echo(String echo);

    Map<String, String> map();
}
