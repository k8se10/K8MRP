package com.example.k8mrp.registry;

import com.example.k8mrp.K8MRPMod;
import com.example.k8mrp.content.RoadSegmentBlock;
import com.example.k8mrp.content.MarkingBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks; import net.minecraft.block.Block;
import net.minecraft.registry.Registries; import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static final Block ROAD_SEGMENT = new RoadSegmentBlock(FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque().strength(1.5F));
  public static final Block MARKING = new MarkingBlock(FabricBlockSettings.copyOf(Blocks.BARRIER).nonOpaque().noCollision().strength(0.2F));

  public static void registerAll(){
    Registry.register(Registries.BLOCK, id("road_segment"), ROAD_SEGMENT);
    Registry.register(Registries.BLOCK, id("marking"), MARKING);
  }
  public static Identifier id(String p){ return new Identifier(K8MRPMod.MODID, p); }
}
