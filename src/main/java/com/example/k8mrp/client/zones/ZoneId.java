package com.example.k8mrp.client.zones;

public record ZoneId(int gx, int gz) {
  public static final int SIZE = 1000; // blocks
  public static ZoneId fromWorld(double x, double z){
    int gx = (int)Math.floor(x / SIZE);
    int gz = (int)Math.floor(z / SIZE);
    return new ZoneId(gx, gz);
  }
  public String fileName(){ return gx + "_" + gz; }
}
