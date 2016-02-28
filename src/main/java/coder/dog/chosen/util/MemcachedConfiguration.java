package coder.dog.chosen.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by megrez on 16/2/28.
 */
@ConfigurationProperties(prefix = "memcached",locations = "classpath:memcached.properties")
public class MemcachedConfiguration {

    private String host;
    private int port;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }
}
