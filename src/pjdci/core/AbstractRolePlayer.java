package pjdci.core;

/**
 * An abstract super-class to use for all "custom defined" role player
 * objects. Takes care of object equality problems only leaving the
 * <code>objectEquals()</code> method tobe implemented using the member
 * fields of the role player object, just the way any object holding
 * state would implement the <code>equals()</code> method.
 *
 * This class packages the special code needed for implementing
 * the <code>equals()</code> method to facilitate role player objects being compared
 * rather than role wrapper objects as a static method, which can easily
 * be called from the overridden <code>equals()</code> method of
 * sub-classes created purely to allow use of built-in classes as role
 * players. See <code>RolePlayer</code> for further details on the
 * needed implementation in this case.
 */
public abstract class AbstractRolePlayer implements RolePlayer{

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public final boolean equals(Object o) {
        return rolePlayerEquals(this, o);
    }

    /**
     * Packages the special code needed for implementing
     * the <code>equals()</code> method to facilitate role player objects being compared
     * rather than role wrapper objects as a static method, which can easily
     * be called from the overridden <code>equals()</code> method of
     * sub-classes created purely to allow use of built-in classes as role
     * players. See <code>RolePlayer</code> for further details on the
     * needed implementation in this case.
     *
     * @param rolePlayer the role player on which the <code>equals()</code> method
     *                   was invoked.
     * @param o the object to compared with this object.
     * @return <code>true</code> if objects are considered equal.
     */
    public static boolean rolePlayerEquals(RolePlayer rolePlayer, Object o){
        if (rolePlayer == o) return true;
        if (o == null) return false;

        if (o instanceof Role){
            Role role = (Role) o;
            return rolePlayer.equals(role.rolePlayer);
        }else{
            return rolePlayer.objectEquals(o);
        }
    }
}
