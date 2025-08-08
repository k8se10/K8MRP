package com.example.k8mrp.client.freecam;

import net.minecraft.client.MinecraftClient;

public class FreecamController {
  private static boolean active = false;
  private static double zoom = 250.0; // blocks (clamped by Zone)
  private static double camX, camY, camZ;

  public static void toggle(){
    active = !active;
    if(active){
      var mc = MinecraftClient.getInstance();
      if(mc.player != null){
        camX = mc.player.getX();
        camZ = mc.player.getZ();
        camY = mc.player.getY() + 100.0;
      }
    }
  }

  public static boolean isActive(){ return active; }
  public static double getZoom(){ return zoom; }
  public static void setZoom(double z){ zoom = Math.max(30.0, Math.min(1000.0, z)); }

  public static void tick(MinecraftClient mc){
    if(!active || mc.player==null) return;
    // Simple top-down feel: lock pitch and slowly float above player
    mc.player.setPitch(90f);
    // Future: custom camera transform & orthographic projection via mixin.
  }
}
