package com.example.k8mrp.client.zones;

import net.minecraft.client.MinecraftClient;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class ZoneManager {
  private static ZoneId active;
  private static boolean dirty = false;

  public static void init(){}

  public static ZoneId getActive(){ return active; }
  public static boolean isDirty(){ return dirty; }
  public static void markDirty(){ dirty = true; }

  public static File worldDataDir(MinecraftClient mc){
    var game = new File(System.getProperty("user.dir"));
    return new File(game, "world/data/k8mrp/zones");
  }

  public static void tick(MinecraftClient mc){
    if(mc.player==null) return;
    var id = ZoneId.fromWorld(mc.player.getX(), mc.player.getZ());
    if(active == null) active = id;
  }

  public static void save(MinecraftClient mc) {
    try{
      var dir = worldDataDir(mc); dir.mkdirs();
      var f = new File(dir, active.fileName() + ".json");
      try(var w = new FileWriter(f)){
        w.write("{"zone":""+active.fileName()+"","saved":""+ LocalDateTime.now() +""}");
      }
      dirty = false;
      // Backup (simple)
      var bakDir = new File(dir.getParentFile(), "backups"); bakDir.mkdirs();
      Files.copy(f.toPath(), new File(bakDir, f.getName()+".bak").toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }catch(Exception e){ e.printStackTrace(); }
  }

  public static void discard(){
    dirty = false; // In a full impl, reload last-saved state
  }
}
