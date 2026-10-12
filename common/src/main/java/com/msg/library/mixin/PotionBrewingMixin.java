package com.msg.library.mixin;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.msg.library.Constants;
import com.msg.library.recipe.brewing.BrewingDisable;
import com.msg.library.recipe.brewing.BrewingDisable.ItemConvertDisable;
import com.msg.library.recipe.brewing.BrewingDisable.PotionMixDisable;
import com.msg.library.recipe.brewing.PotionBrewingMixinAccess;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin implements PotionBrewingMixinAccess {

    @Shadow List<Mix<Potion>> potionMixes;
    @Shadow List<Mix<Item>> containerMixes;

    @Unique private static List<Mix<Potion>> potionMixesAfter;
    @Unique private static List<Mix<Item>> containerMixesAfter;

    @Redirect(
        method = "Lnet/minecraft/world/item/alchemy/PotionBrewing;hasPotionMix(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;potionMixes:Ljava/util/List;",
            opcode = Opcodes.GETFIELD
        )
    )
    private List<Mix<Potion>> redirectPotionMix(PotionBrewing potionBrewing){
        return potionMixesAfter == null ? potionMixesAfter : potionMixesAfter;
    }

    @Redirect(
        method = "Lnet/minecraft/world/item/alchemy/PotionBrewing;isContainerIngredient(Lnet/minecraft/world/item/ItemStack;)Z",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/item/alchemy/PotionBrewing;containerMixes:Ljava/util/List;",
            opcode = Opcodes.GETFIELD
        )
    )
    private List<Mix<Item>> redirectContainerMixes(PotionBrewing potionBrewing){
        return containerMixesAfter == null ? containerMixes : containerMixesAfter;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDatapackReload() {
        potionMixesAfter = new ArrayList<Mix<Potion>>(this.potionMixes);
        containerMixesAfter = new ArrayList<Mix<Item>>(this.containerMixes);

        for (PotionMixDisable mixDisable : BrewingDisable.allDisablePotion){
            potionMixesAfter.removeIf(mix -> {

                if (mixDisable.input().is(mix.from()))

                    for (Holder<Item> item : mix.ingredient().items().toList())
                        if (mixDisable.reagent().is(item)) return true;

                return false;
            });
        }

        for (ItemConvertDisable mixDisable : BrewingDisable.allDisableItemConvert)
            containerMixesAfter.removeIf(mix -> {

                if (mixDisable.input().is(mix.from())) 

                    for (Holder<Item> item : mix.ingredient().items().toList())
                        if (mixDisable.reagent().is(item)) return true;

                return false;
            });

    }

}
