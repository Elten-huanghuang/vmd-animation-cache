package com.team.venom.vmdcache.mixin.slashblade;

import mods.flammpfeil.slashblade.compat.playerAnim.VmdAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VmdAnimation.class, remap = false)
public abstract class VmdAnimationCacheMixin {

    @Shadow
    int currentTick;

    @Unique
    private int vmdcache$lastTick = -1;

    @Unique
    private float vmdcache$lastPartial = -1.0F;

    @Inject(method = "setupAnim", at = @At("HEAD"), cancellable = true, remap = false)
    private void vmdcache$skipIfCached(float partialTick, CallbackInfo ci) {
        if (this.currentTick == this.vmdcache$lastTick && partialTick == this.vmdcache$lastPartial) {
            ci.cancel();
            return;
        }
        this.vmdcache$lastTick = this.currentTick;
        this.vmdcache$lastPartial = partialTick;
    }
}
