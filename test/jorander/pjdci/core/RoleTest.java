package jorander.pjdci.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

@SuppressWarnings({"SuspiciousMethodCalls", "EqualsBetweenInconvertibleTypes"})
public class RoleTest {

    @Test
    public void holdsAndReturnsRolePlayer() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        Role<RolePlayerExtendingBuiltInClass> instance = new TestableRoleForBuiltInRolePlayer(rolePlayer);
        assertSame("Same role player object should be returned", rolePlayer, instance.rolePlayer);
    }

    @Test
    public void runsEqualsAndHashCodeOnTheRolePlayer() {
        final ValueObject rolePlayer1 = new ValueObject(1);
        final ValueObject rolePlayerWithSameValueAsRolePlayer1 = new ValueObject(rolePlayer1.getValue());
        final ValueObject rolePlayer2 = new ValueObject(2);

        assertNotSame("Ensure test data is correct", rolePlayer1, rolePlayerWithSameValueAsRolePlayer1);
        assertTrue("Ensure test data is correct", rolePlayer1.equals(rolePlayerWithSameValueAsRolePlayer1));
        assertFalse("Ensure test data is correct", rolePlayer1.equals(rolePlayer2));

        final Role<ValueObject> role1 = new TestableRole(rolePlayer1);
        final Role<ValueObject> roleWithSameRolePlayerValueAsRole1 = new TestableRole(rolePlayerWithSameValueAsRolePlayer1);
        final Role<ValueObject> role2 = new TestableRole(new ValueObject(2));

        assertTrue("Role is equal to self", role1.equals(role1));
        assertTrue("Roles with same role player value are equal", role1.equals(roleWithSameRolePlayerValueAsRole1));
        assertFalse("Roles with different role player value are not equal", role1.equals(role2));

        assertTrue("Roles with same role player value should have same hash code", role1.hashCode() == roleWithSameRolePlayerValueAsRole1.hashCode());
    }

    @Test
    public void roleAndRolePlayerAreEquivalent_BuiltInClass() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        final Role<RolePlayerExtendingBuiltInClass> role = new TestableRoleForBuiltInRolePlayer(rolePlayer);

        assertTrue("Role equals role player", role.equals(rolePlayer));
    }

    @Test
    public void rolePlayerAndRoleAreEquivalent_BuiltInClass() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        final Role<RolePlayerExtendingBuiltInClass> role = new TestableRoleForBuiltInRolePlayer(rolePlayer);

        assertTrue("Role player equals role", rolePlayer.equals(role));
    }

    @Test
    public void runsToStringOnTheRolePlayer() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        final Role<RolePlayerExtendingBuiltInClass> instance = new TestableRoleForBuiltInRolePlayer(rolePlayer);

        assertEquals("toString should return same as for rolePlayer", rolePlayer.toString(), instance.toString());
    }

    @Test
    public void sameRolePlayerInDifferentRolesAreEqual() {
        final ValueObject rolePlayer = new ValueObject(1);
        final Role<ValueObject> role1 = new TestableRole(rolePlayer);
        final Role<ValueObject> role2 = new AnotherTestableRole(rolePlayer);

        assertTrue("Same role player so should be equal", role1.equals(role2));
    }

    @Test
    public void isPossibleToUseAsKeyInAMap_BuiltInClass() {
        Role<RolePlayerExtendingBuiltInClass> instance = new TestableRoleForBuiltInRolePlayer(new RolePlayerExtendingBuiltInClass());
        Map<Role<RolePlayerExtendingBuiltInClass>, Integer> map = new HashMap<>();
        map.put(instance, 1);
        assertNotNull(map.get(instance));
    }

    @Test
    public void isPossibleToUseToQueryMapWhereRolePlayerIsUsedAsKey_BuiltInClass() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        Role<RolePlayerExtendingBuiltInClass> role = new TestableRoleForBuiltInRolePlayer(rolePlayer);
        Map<RolePlayerExtendingBuiltInClass, Integer> map = new HashMap<>();
        map.put(rolePlayer, 1);

        assertNotNull(map.get(role));
    }

    @Test
    public void isPossibleToUseRolePlayerToQueryMapWhereRoleIsUsedAsKey_BuiltInClass() {
        final RolePlayerExtendingBuiltInClass rolePlayer = new RolePlayerExtendingBuiltInClass();
        Role<RolePlayerExtendingBuiltInClass> role = new TestableRoleForBuiltInRolePlayer(rolePlayer);
        Map<Role<RolePlayerExtendingBuiltInClass>, Integer> map = new HashMap<>();
        map.put(role, 1);

        assertNotNull(map.get(rolePlayer));
    }

    @Test
    public void roleAndRolePlayerAreEquivalent_CustomClass() {
        final ValueObject rolePlayer = new ValueObject(1);
        final Role<ValueObject> role = new TestableRole(rolePlayer);

        assertTrue("Role equals role player", role.equals(rolePlayer));
    }

    @Test
    public void rolePlayerAndRoleAreEquivalent_CustomClass() {
        final ValueObject rolePlayer = new ValueObject(1);
        final Role<ValueObject> role = new TestableRole(rolePlayer);

        assertTrue("Role player equals role", rolePlayer.equals(role));
    }

    @Test
    public void isPossibleToUseAsKeyInAMap_CustomClass() {
        Role<ValueObject> instance = new TestableRole(new ValueObject(1));
        Map<Role<ValueObject>, Integer> map = new HashMap<>();
        map.put(instance, 1);
        assertNotNull(map.get(instance));
    }

    @Test
    public void isPossibleToUseToQueryMapWhereRolePlayerIsUsedAsKey_CustomClass() {
        final ValueObject rolePlayer = new ValueObject(1);
        Role<ValueObject> role = new TestableRole(rolePlayer);
        Map<ValueObject, Integer> map = new HashMap<>();
        map.put(rolePlayer, 1);

        assertNotNull(map.get(role));
    }

    @Test
    public void isPossibleToUseRolePlayerToQueryMapWhereRoleIsUsedAsKey_CustomClass() {
        final ValueObject rolePlayer = new ValueObject(1);
        Role<ValueObject> role = new TestableRole(rolePlayer);
        Map<Role<ValueObject>, Integer> map = new HashMap<>();
        map.put(role, 1);

        assertNotNull(map.get(rolePlayer));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doNotAcceptNullAsRolePlayer() {
        new TestableRole(null);
    }

    private static class TestableRole extends Role<ValueObject> {
        public TestableRole(ValueObject rolePlayer) {
            super(rolePlayer);
        }
    }

    private static class AnotherTestableRole extends Role<ValueObject> {
        public AnotherTestableRole(ValueObject rolePlayer) {
            super(rolePlayer);
        }
    }

    private static class TestableRoleForBuiltInRolePlayer extends Role<RolePlayerExtendingBuiltInClass> {
        public TestableRoleForBuiltInRolePlayer(RolePlayerExtendingBuiltInClass rolePlayer) {
            super(rolePlayer);
        }
    }

    private static class ValueObject extends AbstractRolePlayer {
        private final Object value;

        private ValueObject(Object value) {
            this.value = value;
        }

        @Override
        public boolean objectEquals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ValueObject that = (ValueObject) o;

            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "ValueObject{" +
                    "value=" + value +
                    '}';
        }

        public Object getValue() {
            return value;
        }
    }

    private static class RolePlayerExtendingBuiltInClass /*extends Object*/ implements RolePlayer {
        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object o) {
            return AbstractRolePlayer.rolePlayerEquals(this, o);
        }

        @Override
        public boolean objectEquals(Object o) {
            return super.equals(o);
        }
    }
}
