package com.igorternyuk.texasholdem.model.player;

/**
 * Created by igor on 20.03.18.
 */
public enum PlayerType {
    HUMAN {
        @Override
        public boolean isHuman() {
            return true;
        }

        @Override
        public boolean isComputer() {
            return false;
        }
    },
    COMPUTER {
        @Override
        public boolean isHuman() {
            return false;
        }

        @Override
        public boolean isComputer() {
            return true;
        }
    };
    public abstract boolean isHuman();
    public abstract boolean isComputer();
}
