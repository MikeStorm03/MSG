/*
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.msg.library.networking.ConnectionHandling;

import net.minecraft.world.inventory.BrewingStandMenu.PotionSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.alchemy.PotionBrewing;
@Mixin(PotionSlot.class)
public class PotionSlotNeoForgeMixin {
    @Inject(method = "Lnet/minecraft/world/inventory/BrewingStandMenu$PotionSlot;mayPlaceItem(Lnet/minecraft/world/item/alchemy/PotionBrewing;Lnet/minecraft/world/item/ItemStack;)Z",
            at = @At("RETURN"),
            cancellable = true)
    private static void injected(PotionBrewing potionBrewing, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (ConnectionHandling.allBrewInputs != null)
            for (ItemStackTemplate brewInput : ConnectionHandling.allBrewInputs)
                if (itemStack.is(brewInput.item())) {
                    cir.setReturnValue(true);
                    break;
                }
    }
}
