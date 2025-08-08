package com.example.k8mrp.client.markings;

import com.example.k8mrp.content.MarkingBlock;
import com.example.k8mrp.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;

public class MarkingEngine {
  private static boolean isRoad(net.minecraft.world.World world, BlockPos pos){
    return world.getBlockState(pos).getBlock() instanceof com.example.k8mrp.content.RoadSegmentBlock;
  }

  public static void applyNearPlayer(MinecraftClient mc){
    if(mc.world==null || mc.player==null) return;
    var world = mc.world; var player = mc.player;
    final int R = 64;
    BlockPos origin = player.getBlockPos();

    for(int dx=-R; dx<=R; dx++){
      for(int dz=-R; dz<=R; dz++){
        int x = origin.getX()+dx, z = origin.getZ()+dz;
        int ySurface = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z);
        BlockPos at = new BlockPos(x, ySurface, z);
        if(!isRoad(world, at)) continue;

        // Neighbors
        boolean n = isRoad(world, at.north());
        boolean e = isRoad(world, at.east());
        boolean s = isRoad(world, at.south());
        boolean w = isRoad(world, at.west());
        int deg = (n?1:0) + (e?1:0) + (s?1:0) + (w?1:0);

        if(deg >= 3){
          // Junction center. Place give-way on N/S approaches; arrows one block behind.
          placeApproach(world, at, Direction.NORTH);
          placeApproach(world, at, Direction.SOUTH);
        } else if(deg == 2 && ((n && s) || (e && w))) {
          // Straight segment: occasional roundel 30
          if(((x + z) & 31) == 0){
            setMarking(world, at.up(), MarkingBlock.Variant.ROUND_30, Direction.NORTH);
          }
        } else if(deg == 2) {
          // Curve: optional arrow ahead to guide
          setMarking(world, at.up(), MarkingBlock.Variant.ARROW_AHEAD, Direction.NORTH);
        }
      }
    }
  }

  private static void placeApproach(net.minecraft.world.World world, BlockPos center, Direction dir){
    BlockPos linePos = center.offset(dir, 2).up();
    // Give-way line
    setMarking(world, linePos, MarkingBlock.Variant.GIVE_WAY_LINE, dir);
    // Triangles just behind the line
    setMarking(world, center.offset(dir, 3).up(), MarkingBlock.Variant.GIVE_WAY_TRIANGLES, dir);
    // Arrow further back — left/right bias on side roads
    MarkingBlock.Variant arrow = (dir==Direction.NORTH || dir==Direction.SOUTH)
      ? MarkingBlock.Variant.ARROW_AHEAD
      : (dir==Direction.EAST ? MarkingBlock.Variant.ARROW_RIGHT : MarkingBlock.Variant.ARROW_LEFT);
    setMarking(world, center.offset(dir, 5).up(), arrow, dir);
  }

  private static void setMarking(net.minecraft.world.World world, BlockPos pos, MarkingBlock.Variant v, Direction facing){
    BlockState s = ModBlocks.MARKING.getDefaultState().with(MarkingBlock.VARIANT, v).with(net.minecraft.state.property.Properties.HORIZONTAL_FACING, facing);
    world.setBlockState(pos, s, 3);
  }
}
