package com.magnesnasamice.cnana.foundation.util;

import com.magnesnasamice.cnana.foundation.data.PlayerData;

public class RadiationUtil {
    public static int increaseRadiation(PlayerData playerState, int amount) {
        int radiation = playerState.radiationPercent;
        if (radiation + amount > 24) {
            radiation = 24;
        } else {
            radiation += amount;
        }

        playerState.radiationPercent = radiation;
        return radiation;
    }

    public static int decreaseRadiation(PlayerData playerState, int amount) {
        int radiation = playerState.radiationPercent;
        if (radiation - amount < 0) {
            radiation = 0;
        } else {
            radiation -= amount;
        }

        playerState.radiationPercent = radiation;
        return radiation;
    }
}
