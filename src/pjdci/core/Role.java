package pjdci.core;

/**
 * An abstract super class used for implementing Roles inside a DCI context.
 * A role in a context is implemented through a private static wrapper
 * class with role methods as public methods and any supporting methods
 * needed as private methods.
 * This super class takes care of the wrapping part where the role player object
 * is stored in the protected final <code>rolePlayer</code> field. This super class,
 * together with the <code>RolePlayer</code> interface and the <code>AbstractRolePlayer</code>
 * super class, makes sure <code>equals(), hashCode() and toString()</code> methods are all
 * properly implemented, e.i. targets the role player object instead of the wrapping
 * role implementation. See the <code>RoleTest</code> class for examples of how
 * this is working.
 * As long as the familiar Java-rule of using <code>equals()</code> instead of
 * <code>==</code> for object comparisons, is followed this takes care of the
 * object identification problems the could arise within a CDI-implementation
 * based on object wrapping.
 *
 * @param <RP> the type or interface describing the role contract, i.e. the
 *            contract the role player must implement.
 */
public abstract class Role<RP extends RolePlayer> {
    protected final RP rolePlayer;

    protected Role(RP rolePlayer) {
        if (rolePlayer == null) throw new IllegalArgumentException("Role player cannot be null");
        this.rolePlayer = rolePlayer;
    }

    /**
     * This method is final to stop further overriding. Since roles do not hold state, there is
     * no reason to specify a custom <code>equals()</code> method.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof Role) {
            Role role = (Role) o;
            return rolePlayer.objectEquals(role.rolePlayer);
        } else {
            return rolePlayer.objectEquals(o);
        }
    }

    /**
     * This method is final to stop further overriding. Since roles do not hold state, there is
     * no reason to specify a custom <code>hashCode()</code> method.
     */
    @Override
    public final int hashCode() {
        return rolePlayer != null ? rolePlayer.hashCode() : 0;
    }

    /**
     * This method is final to stop further overriding. Since roles do not hold state, there is
     * no reason to specify a custom <code>toString()</code> method.
     */
    @Override
    public final String toString() {
        return rolePlayer.toString();
    }
}