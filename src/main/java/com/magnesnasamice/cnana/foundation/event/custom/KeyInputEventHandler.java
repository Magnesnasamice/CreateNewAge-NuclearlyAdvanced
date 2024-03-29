package com.magnesnasamice.cnana.foundation.event.custom;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;

public class KeyInputEventHandler {
    // public static final String KEY_CATEGORY_CNANA = "key.category.cnana.main";
    // public static final String KEY_EXAMPLE = "key.cnana.radiation_increment";

    public static KeyBinding exampleKey;

    private static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (exampleKey.wasPressed()) {}
        });
    }

    public static void register() {
//        exampleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                KEY_EXAMPLE,
//                InputUtil.Type.KEYSYM,
//                GLFW.GLFW_KEY_PAGE_UP,
//                KEY_CATEGORY_CNANA
//        ));

        registerKeyInputs();
    }
}
