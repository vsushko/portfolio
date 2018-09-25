package ru.mascom;

import java.util.HashMap;
import java.util.Map;

/**
 * Commands
 *
 * @author vsushko
 */
public enum Command {
    /**
     * req_pq
     */
    REQ_PQ("req_pq"),

    /**
     * res_pq
     */
    RES_PQ("res_pq"),

    /**
     * req_DH_params
     */
    REQ_DH_PARAMS("req_DH_params"),

    /**
     * server_DH_params_ok
     */
    SERVER_DH_PARAMS_OK("server_DH_params_ok"),

    /**
     * Unknown
     */
    UNKNOWN("Unknown");

    /**
     * Command value
     */
    private String value;

    /**
     * Lookup map for getting a Command from passed key
     */
    private static final Map<String, Command> lookup = new HashMap<>();

    static {
        for (Command command : Command.values()) {
            lookup.put(command.getValue(), command);
        }
    }

    /**
     * Constructor with command value
     *
     * @param value the value
     */
    Command(String value) {
        this.value = value;
    }

    /**
     * @return the {@link #value}
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns Command by value
     *
     * @param value the value
     * @return command
     */
    public static Command get(String value) {
        return lookup.get(value);
    }
}
