package me.kvdpxne.covilo.shared;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ApacheEqualsBuilder
  extends EqualsBuilder {

  public ApacheEqualsBuilder append(
    final String a,
    final String b,
    final boolean ignoreCase
  ) {
    if (!this.isEquals()) {
      return this;
    }

    if (null == a || null == b) {
      this.setEquals(false);
      return this;
    }

    this.setEquals(
      ignoreCase
        ? a.equalsIgnoreCase(b)
        : a.equals(b)
    );
    return this;
  }

  public ApacheEqualsBuilder append(
    final String a,
    final String b
  ) {
    return this.append(a, b, false);
  }

  public ApacheEqualsBuilder appendIgnoreCase(
    final String a,
    final String b
  ) {
    return this.append(a, b, true);
  }
}
