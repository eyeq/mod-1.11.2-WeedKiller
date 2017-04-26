package eyeq.weedkiller;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import eyeq.weedkiller.item.ItemWeedkiller;
import eyeq.weedkiller.item.ItemWeedkillerFertilizer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;

import static eyeq.weedkiller.WeedKiller.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class WeedKiller {
    public static final String MOD_ID = "eyeq_weedkiller";

    @Mod.Instance(MOD_ID)
    public static WeedKiller instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item weedKiller;
    public static Item fertilizerWeedkiller;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        weedKiller = new ItemWeedkiller().setUnlocalizedName("weedkiller");
        fertilizerWeedkiller = new ItemWeedkillerFertilizer().setUnlocalizedName("fertilizerWeedkiller");

        GameRegistry.register(weedKiller, resource.createResourceLocation("weedkiller"));
        GameRegistry.register(fertilizerWeedkiller, resource.createResourceLocation("fertilizer_weedkiller"));
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(weedKiller), UOreDictionary.OREDICT_REDSTONE, Items.SPIDER_EYE));
        GameRegistry.addShapelessRecipe(new ItemStack(fertilizerWeedkiller), weedKiller, new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()));
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(weedKiller);
        UModelLoader.setCustomModelResourceLocation(fertilizerWeedkiller);
    }
	
    public static void createFiles() {
    	File project = new File("../1.11.2-WeedKiller");
    	
        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, weedKiller, "Weedkiller");
        language.register(LanguageResourceManager.JA_JP, weedKiller, "除草剤");
        language.register(LanguageResourceManager.EN_US, fertilizerWeedkiller, "Fertilizer with Weedkiller");
        language.register(LanguageResourceManager.JA_JP, fertilizerWeedkiller, "除草剤入り肥料");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, weedKiller, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
        UModelCreator.createItemJson(project, fertilizerWeedkiller, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
