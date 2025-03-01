package com.ewm.card.ewmhealthchecktool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.ewm.card.ewmhealthchecktool.utils.ServiceHealthStatus.DOWN;
import static com.ewm.card.ewmhealthchecktool.utils.ServiceHealthStatus.UP;

public final class ServiceHealthUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServiceHealthUtils.class);

    private static final String DEFAULT_IP_ADDRESS = "0.0.0.0";

    private ServiceHealthUtils() {
        throw new UnsupportedOperationException();
    }

    public static ServiceHealthStatus getServiceStatus(int port) {
        return isInUsed(port) ? UP : DOWN;
    }

    public static String getServiceIpAddress(int port) {
        AtomicReference<String> ssIpAddress = new AtomicReference<>(DEFAULT_IP_ADDRESS);
        AtomicReference<String> dsIpAddress = new AtomicReference<>(DEFAULT_IP_ADDRESS);

        checkPortIsInUseAdvanced(port, closeable -> {
           if (closeable instanceof ServerSocket) {
               ServerSocket ss = (ServerSocket) closeable;
               ssIpAddress.set(ss.getInetAddress().getHostAddress());
           } else if (closeable instanceof DatagramSocket) {
               DatagramSocket ds = (DatagramSocket) closeable;
               dsIpAddress.set(ds.getInetAddress().getHostAddress());
           }
        });

        return ssIpAddress.equals(dsIpAddress) ? ssIpAddress.get() : DEFAULT_IP_ADDRESS;
    }

    /**
     * Checks to see if a specific port is in used.
     *
     * @param port the port to check for availability
     * @see <a href="https://stackoverflow.com/a/435579">This is the implementation coming from the Apache camel project</a>
     */
    public static boolean isInUsed(int port) {
        return checkPortIsInUseAdvanced(port, null);
    }

    private static boolean checkPortIsInUseAdvanced(int port, Consumer<Closeable> getServerIpAddress) {
        if (port < 0) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);

            if (getServerIpAddress != null) {
                getServerIpAddress.accept(ss);
                getServerIpAddress.accept(ds);
            }

            return false;
        } catch (IOException e) {
            logger.warn("Port {} message: {}", port, e.getMessage());
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                }
            }
        }

        return true;
    }
}
