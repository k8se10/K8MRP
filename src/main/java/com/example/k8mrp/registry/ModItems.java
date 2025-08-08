package com.example.k8mrp.registry;
import net.minecraft.item.Item; import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries; import net.minecraft.registry.Registry;
import static com.example.k8mrp.registry.ModBlocks.*;

public class ModItems {
  public static final Item ROAD_SEGMENT_ITEM = new BlockItem(ROAD_SEGMENT, new Item.Settings());
  public static final Item MARKING_ITEM = new BlockItem(MARKING, new Item.Settings());
  public static void registerAll(){
    Registry.register(Registries.ITEM, ModBlocks.id("road_segment"), ROAD_SEGMENT_ITEM);
    Registry.register(Registries.ITEM, ModBlocks.id("marking"), MARKING_ITEM);
  }
}
