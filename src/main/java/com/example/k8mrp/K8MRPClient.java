package com.example.k8mrp;

import com.example.k8mrp.client.freecam.FreecamController;
import com.example.k8mrp.client.lod.LODManager;
import com.example.k8mrp.client.zones.ZoneManager;
import com.example.k8mrp.client.zones.ZoneSwitchScreen;
import com.example.k8mrp.client.markings.MarkingEngine;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class K8MRPClient implements ClientModInitializer {
  private static KeyBinding toggleFreecam, openZoneSwitcher, applyMarkings;

  @Override public void onInitializeClient() {
    toggleFreecam = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.k8mrp.toggle_freecam", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F, "key.categories.misc"));
    openZoneSwitcher = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.k8mrp.open_zone_switcher", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "key.categories.misc"));
    applyMarkings = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.k8mrp.apply_markings", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "key.categories.misc"));

    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      while (toggleFreecam.wasPressed()) FreecamController.toggle();
      while (openZoneSwitcher.wasPressed()) if (client.player != null) client.setScreen(new ZoneSwitchScreen());
      while (applyMarkings.wasPressed()) MarkingEngine.applyNearPlayer(client);
      FreecamController.tick(client);
      ZoneManager.tick(client);
    });

    LODManager.init();
    ZoneManager.init();
  }
}
