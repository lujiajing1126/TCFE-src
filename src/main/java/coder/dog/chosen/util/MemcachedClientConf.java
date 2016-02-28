package coder.dog.chosen.util;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by megrez on 16/2/28.
 */
@Configuration
@EnableConfigurationProperties(MemcachedConfiguration.class)
public class MemcachedClientConf {

    @Autowired
    private MemcachedConfiguration conf;

    @Bean
    public MemcachedClient memcachedClient() {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(String.format("%s:%d",conf.getHost(),conf.getPort())));
        try {
            return builder.build();
        } catch (IOException ex) {
            return null;
        }
    }
}
