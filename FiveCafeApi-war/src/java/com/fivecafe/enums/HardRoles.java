package com.fivecafe.enums;

public enum HardRoles {
    OWNER("owner"),
    COUNTER_STAFF("counter-staff"),
    SERVING_STAFF("serving-staff");
    
    private final String role;
    
    HardRoles(String role) {
        this.role = role;
    }
    
    public final String getRole() {
        return this.role;
    }

    @Override
    public final String toString() {
        return role;
    }
}
