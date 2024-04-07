package me.kvdpxne.covilo.jooq;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * Custom generator strategy for JOOQ code generation with suffix
 * added to table classes.
 */
public final class DefaultGeneratorStrategyWithTableSuffix
  extends DefaultGeneratorStrategy {

  /**
   * Overrides the method to append a suffix to the generated Java
   * class name for tables. If the generation mode is DEFAULT, it appends
   * "Table" to the class name.
   *
   * @param definition The database definition.
   * @param mode The generation mode.
   * @return The modified Java class name with the "Table" suffix.
   */
  @Override
  public String getJavaClassName(
    final Definition definition,
    final Mode mode
  ) {
    // Get the default Java class name
    final String name = super.getJavaClassName(definition, mode);

    // If the name is null or empty, return it without modification
    if (null == name || name.isEmpty()) {
      return name;
    }

    // Append "Table" suffix to the Java class name in DEFAULT mode
    if (Mode.DEFAULT == mode) {
      return name.concat("Table");
    }

    // Return the Java class name without modification for other modes
    return name;
  }
}
