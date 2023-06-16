package me.kvdpxne.covilo.domain.model;

import java.util.Collection;
import java.util.Set;

public enum Role {

  ADMINISTRATOR(
    Integer.MAX_VALUE,
    RolePermission.values()
  ),

  MODERATOR(
    2,
    RolePermission.CREATE,
    RolePermission.DELETE,
    RolePermission.UPDATE,
    RolePermission.READ
  ),

  EDITOR(
    1,
    RolePermission.CREATE,
    RolePermission.UPDATE,
    RolePermission.READ
  ),

  USER(
    0,
    RolePermission.CREATE,
    RolePermission.READ
  ),

  GUEST(
    Integer.MIN_VALUE,
    RolePermission.READ
  );

  private final int order;
  private final Set<RolePermission> permissions;

  Role(final int order, final RolePermission... permissions) {
    this.order = order;
    this.permissions = Set.of(permissions);
  }

  public int getOrder() {
    return this.order;
  }

  /**
   * An unmodifiable collection of role-specific permissions.
   */
  public Collection<RolePermission> getPermissions() {
    return this.permissions;
  }
}
