package org.example.apps.mctg.enums;

public enum ElementType {
    WATER("Water"),
    FIRE("Fire"),
    REGULAR("Regular"),
    NO_TYPE("none");
    private final String type;
    ElementType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
