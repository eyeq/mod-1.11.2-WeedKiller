package eyeq.weedkiller.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWeedkillerFertilizer extends Item {
    public ItemWeedkillerFertilizer() {
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(pos.offset(facing), facing, itemStack)) {
            return EnumActionResult.FAIL;
        }
        boolean flag = ItemDye.applyBonemeal(itemStack, world, pos, player);
        flag |= ItemWeedkiller.weedkill(world, pos, itemRand);
        if(flag) {
            if(!world.isRemote) {
                world.playEvent(2005, pos, 0);
                itemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
