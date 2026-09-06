package com.msg.library.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.msg.library.CommonClass;

import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.minecraft.resources.Identifier;

@Mixin(RegistrySyncManager.class)
public class RegistrySyncManagerMixin {

    @ModifyVariable(method = "createAndPopulateRegistryMap",
            at = @At(value = "STORE"),
            ordinal = 1)
    private static Identifier injected(Identifier id) {
        return id.getNamespace() == "msg" && CommonClass.ignoreNameSpace.contains(id.getPath()) ? null : id;
    }
}
