package me.kvdpxne.covilo.presentation.advices;

/**
 * Represents detailed error information for a specific field.
 *
 * @param field  The name of the field that caused the error.
 * @param errors An array of error messages associated with the field.
 * @since 0.1
 */
public record ErrorDetails(
  String field,
  String[] errors
) {}
