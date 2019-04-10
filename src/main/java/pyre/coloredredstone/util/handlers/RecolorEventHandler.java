package pyre.coloredredstone.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pyre.coloredredstone.blocks.*;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.OreDictionaryUtils;

@Mod.EventBusSubscriber
public class RecolorEventHandler {

    private static final int SHRINK_WIRE = 1;
    private static final int SHRINK_BLOCK = 9;
    private static final int SHRINK_TORCH = 1;
    private static final int SHRINK_REPEATER = 3;
    private static final int SHRINK_COMPARATOR = 3;
    private static final int SHRINK_LAMP = 4;


    @SubscribeEvent
    public static void changeRedstoneTorchColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring && !event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty()) {
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if (dyeColor != null) {
                if ((block == Blocks.REDSTONE_TORCH || block == Blocks.UNLIT_REDSTONE_TORCH) && dyeColor != EnumColor.RED) {
                    IBlockState coloredTorchState;
                    if (block == Blocks.REDSTONE_TORCH) {
                        coloredTorchState = ModBlocks.COLORED_REDSTONE_TORCH.getDefaultState();
                    } else {
                        coloredTorchState = ModBlocks.UNLIT_COLORED_REDSTONE_TORCH.getDefaultState();
                    }
                    IBlockState stateToSet = coloredTorchState
                            .withProperty(BlockColoredRedstoneTorch.FACING, blockState.getValue(BlockRedstoneTorch.FACING))
                            .withProperty(BlockColoredRedstoneTorch.COLOR, dyeColor);
                    setBlockState(event, stateToSet, 3);
                    heldItemMainhand.shrink(SHRINK_TORCH);
                } else if (block == ModBlocks.COLORED_REDSTONE_TORCH || block == ModBlocks.UNLIT_COLORED_REDSTONE_TORCH) {
                    if (dyeColor != EnumColor.RED) {
                        setTileEntityColor(event, dyeColor, SHRINK_TORCH);
                    } else {
                        IBlockState coloredTorchState;
                        if (block == ModBlocks.COLORED_REDSTONE_TORCH) {
                            coloredTorchState = Blocks.REDSTONE_TORCH.getDefaultState();
                        } else {
                            coloredTorchState = Blocks.UNLIT_REDSTONE_TORCH.getDefaultState();
                        }
                        IBlockState stateToSet = coloredTorchState
                                .withProperty(BlockColoredRedstoneTorch.FACING, blockState.getValue(BlockRedstoneTorch.FACING));
                        setBlockState(event, stateToSet, 3);
                        heldItemMainhand.shrink(SHRINK_TORCH);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeRedstoneBlockColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring && !event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty() || heldItemMainhand.getCount() < SHRINK_BLOCK) {
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if (dyeColor != null) {
                if (dyeColor != EnumColor.RED &&
                        (block == Blocks.REDSTONE_BLOCK || (block == ModBlocks.COLORED_REDSTONE_BLOCK && dyeColor != blockState.getValue(BlockColoredRedstone.COLOR)))) {
                    IBlockState stateToSet = ModBlocks.COLORED_REDSTONE_BLOCK
                            .getDefaultState()
                            .withProperty(BlockColoredRedstone.COLOR, dyeColor);
                    setBlockState(event, stateToSet, 2);
                    heldItemMainhand.shrink(SHRINK_BLOCK);
                } else if (block == ModBlocks.COLORED_REDSTONE_BLOCK && dyeColor == EnumColor.RED) {
                    IBlockState stateToSet = Blocks.REDSTONE_BLOCK
                            .getDefaultState();
                    setBlockState(event, stateToSet, 2);
                    heldItemMainhand.shrink(SHRINK_BLOCK);
                }
            }
        }
    }

    @SubscribeEvent
    public static void changeRedstoneRepeaterColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring && event.getEntityPlayer().isSneaking()) {
            changeTileEntityColor(event, BlockColoredRedstoneRepeater.class, SHRINK_REPEATER);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneComparatorColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring && event.getEntityPlayer().isSneaking()) {
            changeTileEntityColor(event, BlockRedstoneComparator.class, SHRINK_COMPARATOR);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneWireColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring) {
            changeTileEntityColor(event, BlockColoredRedstoneWire.class, SHRINK_WIRE);
        }
    }

    @SubscribeEvent
    public static void changeRedstoneLampColor(PlayerInteractEvent.RightClickBlock event) {
        if (CurrentModConfig.inWorldRecoloring) {
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
            if (block == Blocks.REDSTONE_LAMP || block == Blocks.LIT_REDSTONE_LAMP) {
                ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
                if (!heldItemMainhand.isEmpty() && heldItemMainhand.getCount() >= SHRINK_LAMP) {
                    EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
                    if (dyeColor != null) {
                        IBlockState stateToSet = ModBlocks.COLORED_REDSTONE_LAMP.getDefaultState()
                                .withProperty(BlockColoredRedstoneLamp.COLOR, dyeColor);
                        setBlockState(event, stateToSet, 3);
                        heldItemMainhand.shrink(SHRINK_LAMP);
                    }
                }
            } else if (block == ModBlocks.COLORED_REDSTONE_LAMP) {
                changeTileEntityColor(event, BlockColoredRedstoneLamp.class, SHRINK_LAMP);
            }
        }
    }

    private static void changeTileEntityColor(PlayerInteractEvent.RightClickBlock event, Class clazz, int shrink) {
        if (CurrentModConfig.inWorldRecoloring && !event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            ItemStack heldItemMainhand = event.getEntityPlayer().getHeldItemMainhand();
            if (heldItemMainhand.isEmpty() || heldItemMainhand.getCount() < shrink) {
                return;
            }
            EnumColor dyeColor = OreDictionaryUtils.getDyeColor(heldItemMainhand);
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();

            if (clazz.isInstance(block) && dyeColor != null) {
                setTileEntityColor(event, dyeColor, shrink);
            }
        }
    }

    private static void setTileEntityColor(PlayerInteractEvent.RightClickBlock event, EnumColor dyeColor, int shrink) {
        TileEntityColored tileEntity = (TileEntityColored) event.getWorld().getTileEntity(event.getPos());
        if (tileEntity.getColor() != dyeColor) {
            tileEntity.setColor(dyeColor);
            event.setCanceled(true);
            event.getEntityPlayer().getHeldItemMainhand().shrink(shrink);
        }
    }

    private static void setBlockState(PlayerInteractEvent.RightClickBlock event, IBlockState stateToSet, int flags) {
        event.getWorld().setBlockState(event.getPos(), stateToSet, flags);
        event.setCanceled(true);
    }
}
