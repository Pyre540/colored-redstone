package pyre.coloredredstone.blocks;

import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pyre.coloredredstone.ColoredRedstone;
import pyre.coloredredstone.config.CurrentModConfig;
import pyre.coloredredstone.init.ModBlocks;
import pyre.coloredredstone.init.ModItems;
import pyre.coloredredstone.init.ModMaterials;
import pyre.coloredredstone.util.EnumColor;
import pyre.coloredredstone.util.exceptions.PrivateMethodInvocationException;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import static pyre.coloredredstone.util.EnumColor.RED;

@SuppressWarnings("NullableProblems")
public class BlockColoredRedstoneComparator extends BlockRedstoneComparator implements IColoredFeatures, IBlockColoredTE<TileEntityColoredRedstoneComparator> {

    private Method calculateOutput;

    public BlockColoredRedstoneComparator(String name, boolean powered) {
        super(powered);
        setRegistryName(name);
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);
        disableStats();
        this.setDefaultState(super.getDefaultState().withProperty(COLOR, RED));

        ModBlocks.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, MODE, POWERED, COLOR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState newState = super.getActualState(state, worldIn, pos);
        return newState.withProperty(COLOR, getColor(worldIn, pos));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(COLOR, EnumColor.byMetadata(meta));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityColoredRedstoneComparator(state.getValue(COLOR));
    }

    @Nullable
    @Override
    public TileEntityColoredRedstoneComparator getTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity tileEntity =  world instanceof ChunkCache ?
                        ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) :
                        world.getTileEntity(pos);
        if (world instanceof World && !(tileEntity instanceof TileEntityColoredRedstoneComparator)) { //fix block and te if it's vanilla one
            ((World)world).removeTileEntity(pos);
            IBlockState originalBlockstate = world.getBlockState(pos);
            IBlockState newBlockState = this.getDefaultState().withProperty(FACING, originalBlockstate.getValue(FACING))
                    .withProperty(MODE, originalBlockstate.getValue(MODE))
                    .withProperty(POWERED, originalBlockstate.getValue(POWERED));
            ((World)world).setBlockState(pos, newBlockState);

            return (TileEntityColoredRedstoneComparator) world.getTileEntity(pos);
        }
        return (TileEntityColoredRedstoneComparator) tileEntity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Material getMaterial(IBlockState state) {
        return (CurrentModConfig.waterproof && state.getValue(COLOR) == WATERPROOF_COLOR) ?
                ModMaterials.CIRCUITS_WATERPROOF : super.getMaterial(state);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return (CurrentModConfig.explosionproof && getColor(world, pos) == EXPLOSION_PROOF_COLOR) ?
                EXPLOSION_PROOF_BLOCK_RESISTANCE : super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        EnumColor color = getColor(world, pos);
        return color != RED ? new ItemStack(ModItems.COLORED_REDSTONE_COMPARATOR, 1, color.getMetadata()) : new ItemStack(Items.COMPARATOR);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IBlockState actualState = getActualState(state, world, pos);
        super.getDrops(drops, world, pos, actualState, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(COLOR) != RED ? ModItems.COLORED_REDSTONE_COMPARATOR : Items.COMPARATOR;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR) != RED ? state.getValue(COLOR).getMetadata() : 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        Arrays.stream(EnumColor.values())
                .filter(color -> color != RED)
                .forEach(color -> items.add(new ItemStack(this, 1, color.getMetadata())));
    }

    //preserved TileEntity until after #getDrops has been called
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    //preserved TileEntity until after #getDrops has been called
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        // If it will harvest, delay deletion of the block until after #getDrops
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.notifyNeighbors(worldIn, pos, state);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        this.notifyNeighbors(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.capabilities.allowEdit) {
            return false;
        } else {
            IBlockState newState = state.cycleProperty(MODE);
            float f = newState.getValue(MODE) == BlockRedstoneComparator.Mode.SUBTRACT ? 0.55F : 0.5F;
            worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
            worldIn.setBlockState(pos, newState, 2);
            this.onStateChange(worldIn, pos, newState);
            return true;
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (this.isRepeaterPowered) {
            worldIn.setBlockState(pos, this.getUnpoweredState(state).withProperty(POWERED, Boolean.TRUE), 4);
        }
        this.onStateChange(worldIn, pos, state);
    }

    @Override
    protected IBlockState getPoweredState(IBlockState unpoweredState) {
        Boolean isPowered = unpoweredState.getValue(POWERED);
        BlockRedstoneComparator.Mode comparatorMode = unpoweredState.getValue(MODE);
        EnumFacing facing = unpoweredState.getValue(FACING);
        EnumColor color = unpoweredState.getValue(COLOR);
        return Blocks.POWERED_COMPARATOR.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(POWERED, isPowered)
                .withProperty(MODE, comparatorMode)
                .withProperty(COLOR, color);
    }

    @Override
    protected IBlockState getUnpoweredState(IBlockState poweredState) {
        Boolean isPowered = poweredState.getValue(POWERED);
        BlockRedstoneComparator.Mode comparatorMode = poweredState.getValue(MODE);
        EnumFacing facing = poweredState.getValue(FACING);
        EnumColor color = poweredState.getValue(COLOR);
        return Blocks.UNPOWERED_COMPARATOR.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(POWERED, isPowered)
                .withProperty(MODE, comparatorMode)
                .withProperty(COLOR, color);
    }

    @Override
    protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        TileEntityColoredRedstoneComparator tileEntity = getTileEntity(worldIn, pos);
        return tileEntity != null ? tileEntity.getOutputSignal() : 0;
    }

    @Override
    protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isBlockTickPending(pos, this)) {
            int i = this.calculateOutput(worldIn, pos, state);
            TileEntityColoredRedstoneComparator tileEntity = getTileEntity(worldIn, pos);
            int j = tileEntity != null ? tileEntity.getOutputSignal() : 0;

            if (i != j || this.isPowered(state) != this.shouldBePowered(worldIn, pos, state)) {
                if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
                    worldIn.updateBlockTick(pos, this, 2, -1);
                } else {
                    worldIn.updateBlockTick(pos, this, 2, 0);
                }
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        if (!worldIn.isRemote && entityIn instanceof EntityLivingBase && (worldIn.getWorldTime() % 20 == 0)) {
            EnumColor color = getColor(worldIn, pos);

            if (color.equals(WITHERING_COLOR)) {
                withering(worldIn, entityIn);
            } else if (color.equals(SLUGGISH_COLOR)) {
                sluggish(worldIn, entityIn);
            } else if (color.equals(SPEEDY_COLOR)) {
                speedy(worldIn, entityIn);
            } else if (color.equals(HEALTHY_COLOR)) {
                healthy(worldIn, entityIn);
            }
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        EnumColor color = getColor(world, pos);
        if (CurrentModConfig.burnable && CurrentModConfig.burnableCatchFire && color.equals(BURNABLE_COLOR)) {
            return BURNABLE_FLAMMABILITY;
        }
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        EnumColor color = getColor(world, pos);
        if (CurrentModConfig.burnable && CurrentModConfig.burnableCatchFire && color.equals(BURNABLE_COLOR)) {
            return BURNABLE_FIRE_SPREAD_SPEED;
        }
        return 0;
    }

    private void onStateChange(World worldIn, BlockPos pos, IBlockState state) {
        int i = this.calculateOutput(worldIn, pos, state);
        TileEntityColoredRedstoneComparator tileEntity = getTileEntity(worldIn, pos);
        int j = 0;

        if (tileEntity != null) {
            j = tileEntity.getOutputSignal();
            tileEntity.setOutputSignal(i);
        }

        if (j != i || state.getValue(MODE) == BlockRedstoneComparator.Mode.COMPARE) {
            boolean flag1 = this.shouldBePowered(worldIn, pos, state);
            boolean flag = this.isPowered(state);

            if (flag && !flag1) {
                worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.FALSE), 2);
            } else if (!flag && flag1) {
                worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.TRUE), 2);
            }

            this.notifyNeighbors(worldIn, pos, state);
        }
    }

    private int calculateOutput(World worldIn, BlockPos pos, IBlockState state) {
        if (calculateOutput == null) {
            calculateOutput = ReflectionHelper.findMethod(this.getClass().getSuperclass(), "calculateOutput", "func_176460_j", World.class, BlockPos.class, IBlockState.class);
        }
        try {
            return (int) calculateOutput.invoke(this, worldIn, pos, state);
        } catch (IllegalAccessException | InvocationTargetException e) {
            ColoredRedstone.logger.error("Cannot invoke 'calculateOutput' method for ColoredRedstoneComparator.", e);
            throw new PrivateMethodInvocationException("Cannot invoke 'calculateOutput' method for ColoredRedstoneComparator.", e);
        }
    }
}
