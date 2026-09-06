package com.msg.library.platform.services;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *s
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }
}