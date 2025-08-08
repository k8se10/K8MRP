package com.example.k8mrp.client.zones;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class ZoneSwitchScreen extends Screen {
  public ZoneSwitchScreen(){ super(Text.literal("K8MRP — Zone Switcher")); }

  @Override public boolean shouldCloseOnEsc(){ return true; }

  @Override public void render(DrawContext dc, int mx, int my, float d){
    super.render(dc, mx, my, d);
    var id = ZoneManager.getActive();
    String info = "Current zone: " + (id==null ? "Detecting..." : id.fileName()) + (ZoneManager.isDirty() ? " (Unsaved changes)" : "");
    dc.drawCenteredTextWithShadow(textRenderer, info, width/2, 20, 0xFFFFFF);
    dc.drawCenteredTextWithShadow(textRenderer, "[S] Save & Switch   [D] Discard & Switch   [ESC] Cancel", width/2, 40, 0xA0A0A0);
  }

  @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers){
    var mc = MinecraftClient.getInstance();
    switch(keyCode){
      case 83: // S
        ZoneManager.save(mc);
        this.close();
        return true;
      case 68: // D
        ZoneManager.discard();
        this.close();
        return true;
      default: return super.keyPressed(keyCode, scanCode, modifiers);
    }
  }
}
