package com.chatop.api.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvUtil {
    @Autowired
    Environment environment;

    private String port;
    private String hostname;

    /**
     * Get port.
     *
     * @return
     */
    public String getPort() {
        if (port == null)
            port = environment.getProperty("local.server.port");
        return port;
    }

    /**
     * Get port, as Integer.
     *
     * @return
     */
    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    /**
     * Get hostname.
     *
     * @return
     */
    public String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostAddress();
        return hostname;
    }

    /**
     * Get context path.
     *
     * @return
     */
    public String getContextPath() {
        return environment.getProperty("server.servlet.contextPath");
    }

    public String getServerUrlPrefi() throws UnknownHostException {
        // return "http://" + getHostname() + ":" + getPort() + getContextPath() + "/";
        return "http://localhost:" + getPort() + getContextPath() + "/";
    }
}
