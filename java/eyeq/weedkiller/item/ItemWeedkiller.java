package eyeq.weedkiller.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemWeedkiller extends Item {
    public ItemWeedkiller() {
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(pos.offset(facing), facing, itemStack)) {
            return EnumActionResult.FAIL;
        }
        if(weedkill(world, pos, itemRand)) {
            if(!world.isRemote) {
                world.playEvent(2005, pos, 0);
                itemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    public static boolean weedkill(World world, BlockPos pos, Random rand) {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() == Blocks.TALLGRASS) {
        } else if(Blocks.TALLGRASS.canBlockStay(world, pos, state)) {
            pos = pos.up();
        } else {
            return false;
        }
        for(int i = 0; i < 128; i++) {
            BlockPos pos1 = pos;
            for(int j = 0; ; j++) {
                if(j >= i / 16) {
                    Block block = world.getBlockState(pos1).getBlock();
                    if(block == Blocks.TALLGRASS) {
                        world.setBlockToAir(pos1);
                        break;
                    }
                }
                pos1 = pos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if(!Blocks.TALLGRASS.canBlockStay(world, pos1.down(), world.getBlockState(pos1.down()))) {
                    break;
                }
            }
        }
        return true;
    }
}
