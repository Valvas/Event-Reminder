package fr.hexus.aprivate.mondayreminder.Enums;

/**
 * Created by nicolas on 06/12/17.
 */

public enum ParticipatingStatus {
    YES(2) {
        @Override
        public String toString() {
            return "OUI";
        }
    },

    NO(1) {
        @Override
        public String toString() {
            return "NON";
        }
    },

    WAIT(0) {
        @Override
        public String toString() {
            return "EN ATTENTE";
        }
    };

    private final int value;

    ParticipatingStatus(int i) {
        value = i;
    }

    public int getValue() { return value; }
}
