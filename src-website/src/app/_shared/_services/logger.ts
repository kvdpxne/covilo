/**
 * Type definition for a function that provides logging messages.
 *
 * @returns A string representing the logging message.
 */
export type LoggerMessageProvider = () => string;

/**
 * Service for logging messages to the console with various log levels.
 *
 * This service is provided at the root level to ensure it's available
 * throughout the application.
 */
export class Logger {

  /**
   * Checks whether logging is enabled based on the environment.
   *
   * @returns True if logging is enabled in the current environment,
   * otherwise false.
   */
  public isEnabled(): boolean {
    // Logging is enabled in non-production environments
    return true;
  }

  /**
   * Logs an error message to the console.
   *
   * @param message A function that provides the error message to log.
   * @remarks If logging is enabled, the error message is logged to the
   * console using `console.error()`.
   */
  public error(
    message: LoggerMessageProvider
  ): void {
    if (this.isEnabled()) {
      console.error(message());
    }
  }

  /**
   * Logs a warning message to the console.
   *
   * @param message A function that provides the warning message to log.
   * @remarks If logging is enabled, the warning message is logged to the
   * console using `console.warn()`.
   */
  public warn(
    message: LoggerMessageProvider
  ): void {
    if (this.isEnabled()) {
      console.warn(message());
    }
  }

  /**
   * Logs an informational message to the console.
   *
   * @param message A function that provides the information message to log.
   * @remarks If logging is enabled, the information message is logged to
   * the console using `console.info()`.
   */
  public info(
    message: LoggerMessageProvider
  ): void {
    if (this.isEnabled()) {
      console.info(message());
    }
  }

  /**
   * Logs a debug message to the console.
   *
   * @param message A function that provides the debug message to log.
   * @remarks If logging is enabled, the debug message is logged to the
   * console using `console.debug()`.
   */
  public debug(
    message: LoggerMessageProvider
  ): void {
    if (this.isEnabled()) {
      console.debug(message());
    }
  }

  /**
   * Logs a trace message to the console.
   *
   * @param message A function that provides the trace message to log.
   * @remarks If logging is enabled, the trace message is logged to the
   * console using `console.trace()`.
   */
  public trace(
    message: LoggerMessageProvider
  ): void {
    if (this.isEnabled()) {
      console.trace(message());
    }
  }
}

/**
 *
 */
export const logger: Logger = new Logger();

