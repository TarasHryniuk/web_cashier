package cashier.dao.entity;


/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public enum Role {
    //    CASHIER(0),
//    HIGH_CASHIER(1),
//    MANAGER(2);
    CASHIER, HIGH_CASHIER, MANAGER;

//    private final int mask;
//
//    Role(int id) {
//        mask = 1 << id;
//    }
//
//    public int getMask() {
//        return mask;
//    }
//
//    public Role valueOf(Integer id){
//        for (Role value : values()) {
//            if (value.getMask() == id)
//                return value;
//        }
//        return null;
//    }

    public static Role getRole(User user) {
        int roleId = user.getRole();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
