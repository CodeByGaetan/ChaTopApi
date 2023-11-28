package com.chatop.api.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtility {
    @Autowired
    private Environment environment;

    private String port;
    private String hostname;

    public String getPort() {
        if (port == null) {
            port = environment.getProperty("local.server.port");
        }
        return port;
    }

    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    public String getHostname() {
        if (hostname == null) {
            try {
                hostname = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                // Can't get LocalHost Address
            }
        }
        return hostname;
    }

    public String getContextPath() {
        return environment.getProperty("server.servlet.contextPath");
    }

    public String getServerUrlPrefi() {
        return "http://localhost:" + getPort() + getContextPath() + "/";
    }
}
