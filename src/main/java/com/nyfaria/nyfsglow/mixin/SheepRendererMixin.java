package com.nyfaria.nyfsglow.mixin;

import com.nyfaria.nyfsglow.wrappers.SheepWrapper;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class SheepRendererMixin {


    @Inject(method = "getBlockLight", at=@At("HEAD"), cancellable = true)
    public void getBlockLight(Entity entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if(entity instanceof SheepEntity){
            if(((SheepWrapper)entity).getShouldGlow()){
                cir.setReturnValue(15);
            }
        }
    }

}
