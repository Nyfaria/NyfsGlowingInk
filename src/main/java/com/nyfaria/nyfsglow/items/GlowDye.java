package com.nyfaria.nyfsglow.items;

import com.google.common.collect.Maps;
import com.nyfaria.nyfsglow.wrappers.SheepWrapper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;

import java.util.Map;

public class GlowDye extends Item {
    private static final Map<DyeColor, GlowDye> DYES = Maps.newEnumMap(DyeColor.class);
    private final DyeColor color;

    public GlowDye(DyeColor color, Settings settings) {
        super(settings);
        this.color = color;
        DYES.put(color, this);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof SheepEntity) {
            SheepEntity sheepEntity = (SheepEntity)entity;
            if (sheepEntity.isAlive() && !sheepEntity.isSheared() && sheepEntity.getColor() != this.color) {
                sheepEntity.world.playSoundFromEntity(user, sheepEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                if (!user.world.isClient) {
                    sheepEntity.setColor(this.color);
                    ((SheepWrapper)sheepEntity).setShouldGlow(true);
                    stack.decrement(1);
                }

                return ActionResult.success(user.world.isClient);
            }
        }

        return ActionResult.PASS;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public static GlowDye byColor(DyeColor color) {
        return DYES.get(color);
    }
}