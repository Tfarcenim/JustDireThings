package com.direwolf20.justdirethings.mixin;

import com.direwolf20.justdirethings.common.events.EntityEvents;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick",at = @At("RETURN"))
    private void onTick(CallbackInfo ci){
        EntityEvents.entityTick((ItemEntity) (Object)this);
    }
}
