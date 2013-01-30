package jorander.pjdci.core;

/**
 * This interface describes the basic contract of a role player. Any object
 * being a role player must implement this interface, Otherwise it will
 * not be accepted by the <code>Role</code> super class.
 * The interface defines an alternative method name for defining object equality,
 * since the ordinary <code>equals()</code> method must be implemented
 * in a specific way to facilitate role player objects being compared
 * rather than role wrapper objects.
 *
 * For any "custom defined" role player objects this interface is being implemented
 * by the <code>AbstractRolePlayer</code> super class, which should be
 * inherited from, only leaving the <code>objectEquals()</code> method to
 * be implemented using the member fields of the role player object, just
 * the way any object holding state would implement the <code>equals()</code> method.
 *
 * In cases where an already existing class that cannot be changed, such as
 * Java Collection classes or other built-in classes, is to be used as-is as a
 * role player, a sub-class, implementing this interface, and overriding
 * the <code>equals()</code> method as seen below is necessary to handle
 * object equality correct when a role player object is compared to a role wrapper
 * object. See <code>RoleTest</code> for examples of how this is working.
 *
 * Implementation to use in sub-class:
 * <code>
 *     @Override
 *     public boolean equals(Object o) {
 *          return AbstractRolePlayer.rolePlayerEquals(this, o);
 *     }
 *
 *     @Override
 *     public boolean objectEquals(Object o) {
 *          return super.equals(o);
 *     }
 * </code>
 */
public interface RolePlayer {
    /**
     * For "custom defined" role player objects this method should be implemented
     * using the member fields of the role player object, just the way any object
     * holding state would implement the <code>equals()</code> method.
     *
     * For sub-classes created purely to allow use of built-in classes as role
     * players this method should be implemented as follows:
     *
     * <code>
     *     @Override
     *     public boolean objectEquals(Object o) {
     *          return super.equals(o);
     *     }
     * </code>
     *
     * @param o the object to compared with this object.
     * @return <code>true</code> if objects are considered equal.
     */
    public boolean objectEquals(Object o);
}
