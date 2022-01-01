package com.nyfaria.nyfsglow.mixin;

import com.nyfaria.nyfsglow.NyfsGlow;
import com.nyfaria.nyfsglow.wrappers.SheepWrapper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements SheepWrapper {
    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    public boolean shouldGlow = false;


    @Override
    public boolean getShouldGlow() {
        return shouldGlow;
    }

    @Inject(method = "writeCustomDataToNbt", at=@At("TAIL"))
    public void saveGlow(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("glow", shouldGlow);
    }

    @Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
    public void loadGlow(NbtCompound nbt, CallbackInfo ci){
        shouldGlow = nbt.getBoolean("glow");
    }

    @Override
    public void setShouldGlow(boolean glow) {
        shouldGlow = glow;
        if(this.world.isClient) return;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(this.getId());
        buf.writeBoolean(glow);
        for(ServerPlayerEntity p : PlayerLookup.tracking((ServerWorld)this.world, this.getBlockPos())) {
            ServerPlayNetworking.send(p, new Identifier(NyfsGlow.MOD_ID, "sheepglow"), buf);
        }
    }

}
