package com.example.k8mrp.content;

import net.minecraft.block.*; import net.minecraft.state.*; import net.minecraft.state.property.*; import net.minecraft.util.math.Direction; import net.minecraft.world.BlockView;

public class MarkingBlock extends HorizontalFacingBlock {
  public static final EnumProperty<Variant> VARIANT = EnumProperty.of("variant", Variant.class);
  public enum Variant {
    ARROW_AHEAD, ARROW_LEFT, ARROW_RIGHT,
    GIVE_WAY_TRIANGLES, GIVE_WAY_LINE, STOP_LINE,
    ROUND_20, ROUND_30,
    ZIGZAG, HATCHING, CROSSWALK_ZEBRA
  }
  public MarkingBlock(Settings s){ super(s); this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(VARIANT, Variant.ARROW_AHEAD)); }
  @Override protected void appendProperties(StateManager.Builder<Block, BlockState> b){ b.add(FACING, VARIANT); }
  @Override public BlockRenderType getRenderType(BlockState state){ return BlockRenderType.MODEL; }
  @Override public boolean hasSidedTransparency(BlockState state){ return true; }
  @Override public boolean isTransparent(BlockState state, BlockView world, net.minecraft.util.math.BlockPos pos){ return true; }
}
