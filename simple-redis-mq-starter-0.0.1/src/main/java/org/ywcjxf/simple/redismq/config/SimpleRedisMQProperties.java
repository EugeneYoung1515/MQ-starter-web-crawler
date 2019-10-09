package org.ywcjxf.simple.redismq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simple.redismq")
public class SimpleRedisMQProperties {
    private String defaultqueuename = "first";
    private int consumerthred = 4;
    private long defaultdelay = 0;
    private long defaulttimeout = 60000;

    public String getDefaultqueuename() {
        return defaultqueuename;
    }

    public void setDefaultqueuename(String defaultqueuename) {
        this.defaultqueuename = defaultqueuename;
    }

    public int getConsumerthred() {
        return consumerthred;
    }

    public void setConsumerthred(int consumerthred) {
        this.consumerthred = consumerthred;
    }

    public long getDefaultdelay() {
        return defaultdelay;
    }

    public void setDefaultdelay(long defaultdelay) {
        this.defaultdelay = defaultdelay;
    }

    public long getDefaulttimeout() {
        return defaulttimeout;
    }

    public void setDefaulttimeout(long defaulttimeout) {
        this.defaulttimeout = defaulttimeout;
    }
}
