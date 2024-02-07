package me.kvdpxne.covilo.infrastructure.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The HiddenParameter annotation is used to mark parameters or fields that
 * should be hidden or ignored during processing, such as in serialization
 * or deserialization.
 *
 * <p>
 * Usage:
 * - Apply this annotation to parameters or fields that need to be hidden.
 * <p>
 *
 * Example:
 * <pre>
 * public void process(@HiddenParameter String secretData) {
 *     // Method implementation
 * }
 * </pre>
 *
 * In the above example, the parameter 'secretData' will be hidden during
 * processing.
 */
@Parameter(hidden = true)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HiddenParameter {}
