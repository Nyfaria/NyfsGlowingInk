package com.nyfaria.nyfsglow;

import com.nyfaria.nyfsglow.wrappers.SheepWrapper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class NyfsGlowClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(NyfsGlow.MOD_ID,"sheepglow"), (client, handler, buf, responseSender) -> {
            // Read packet data on the event loop
            if(MinecraftClient.getInstance().world == null) return;
            SheepEntity user = (SheepEntity) MinecraftClient.getInstance().world.getEntityById(buf.readInt());
            boolean glow = buf.readBoolean();

            client.execute(() -> {

                ((SheepWrapper)user).setShouldGlow(glow);

            });
        });
    }
}
