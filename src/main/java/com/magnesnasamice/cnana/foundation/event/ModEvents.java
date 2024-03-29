package com.magnesnasamice.cnana.foundation.event;

import com.magnesnasamice.cnana.foundation.event.custom.HazmatBreathingEventHandler;
import com.magnesnasamice.cnana.foundation.event.custom.RadiationEventHandler;

public class ModEvents {
    public static void register() {
        RadiationEventHandler.register();
        HazmatBreathingEventHandler.register();
        // KeyInputHandler.register();
    }
}
