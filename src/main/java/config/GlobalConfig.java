package config;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public final class GlobalConfig {

    private static Properties config = new Properties();

    /**
     * Keys of config file
     */
    private static final String PORT_KEY = "port";
    private static final String EXIT_KEY = "exit";

    /**
     * Default value.
     */
    private static final String CONFIG_FILENAME = "config.properties";
    private static final String DEFAULT_PORT = "9000";
    private static final String DEFAULT_EXIT_MSG = "exit";

    /**
     * Load config file.
     */
    static {
        try (InputStream inputStream = GlobalConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME)) { // auto close
            config.load(inputStream);
        } catch (IOException e) {
            System.err.println("config file error");
            e.printStackTrace();
        }
        EXIT_MSG = config.getProperty(EXIT_KEY, DEFAULT_EXIT_MSG);
        PORT = Integer.valueOf(config.getProperty(PORT_KEY, DEFAULT_PORT));
    }

    public static final String EXIT_MSG;
    public static final int PORT;
    public static final Charset CHARSET = CharsetUtil.UTF_8;

    private GlobalConfig() {
        throw new UnsupportedOperationException("config.GlobalConfig can not be constructed.");
    }

}
