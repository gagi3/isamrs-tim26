package com.delta.fly.enumeration;

public enum RoleName {

    ROLE_SYSTEMADMIN,
    ROLE_AIRLINECOMPANYADMIN,
    ROLE_PASSENGER;

    public String getName() {
        switch (this) {
            case ROLE_PASSENGER:
                return "ROLE_PASSENGER";
            case ROLE_SYSTEMADMIN:
                return "ROLE_SYSTEMADMIN";
            case ROLE_AIRLINECOMPANYADMIN:
                return "ROLE_AIRLINECOMPANYADMIN";
            default:
                return "";
        }
    }

}
