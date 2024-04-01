/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2024 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_8;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8.EntityType.*;

// TODO, null fields need mappings
// TODO namings, i have no idea how to get good namings for this shit, but we need it at some point
// TODO, exception handling, check how 1.8 <-> 1.9 handles invalid/missing metadata, maybe we need defaults??
// TODO, entities are removing previous added metadata!!
//       FIREBALL != ENTITY
//       THROWABLE != ENTITY
//       FALLING_BLOCK != ENTITY
//       TNT_PRIMED != ENTITY
//       ENDER_EYE != ENTITY
//       ENTITY_HANGING != ENTITY
public enum MetaIndex {

    // Entity
    ENTITY_STATUS(ENTITY, 0, MetaType1_8.Byte, MetaType1_9.Byte),
    ENTITY_AIR(ENTITY, 1, MetaType1_8.Short, MetaType1_9.VarInt),
    ENTITY_ALWAYS_SHOW_NAMETAG(ENTITY, 3, MetaType1_8.Byte, MetaType1_9.Boolean),
    ENTITY_NAMETAG(ENTITY, 2, MetaType1_8.String, MetaType1_9.String),
    ENTITY_SILENT(ENTITY, 4, MetaType1_8.Byte, MetaType1_9.Boolean),

    // Entity living base
    ENTITY_LIVING_BASE_POTION_EFFECT_COLOR(ENTITY_LIVING_BASE, 7, MetaType1_8.Int, null),
    ENTITY_LIVING_BASE_IS_POTION_AMBIENT(ENTITY_LIVING_BASE, 8, MetaType1_8.Byte, null),
    ENTITY_LIVING_BASE_NUMBER_OF_ARROWS_IN(ENTITY_LIVING_BASE, 9, MetaType1_8.Byte, null),
    ENTITY_LIVING_BASE_HEALTH(ENTITY_LIVING_BASE, 6, MetaType1_8.Float, null),

    // Entity living
    ENTITY_LIVING_NO_AI(ENTITY_LIVING, 15, MetaType1_8.Byte, null), // todo, new index

    // Entity ageable
    ENTITY_AGEABLE_AGE(ENTITY_AGEABLE, 12, MetaType1_8.Byte, null), // todo, new index

    // Horse
    HORSE_INFO(HORSE, 16, MetaType1_8.Int, null), // todo, new index
    HORSE_TYPE(HORSE, 19, MetaType1_8.Byte, null),
    HORSE_SUBTYPE(HORSE, 20, MetaType1_8.Int, null),
    HORSE_OWNER(HORSE, 21, MetaType1_8.String, null),
    HORSE_ARMOR(HORSE, 22, MetaType1_8.Int, null),

    // Rabbit
    RABBIT_TYPE(RABBIT, 18, MetaType1_8.Byte, null), // todo, new index

    // Entity tameable
    TAMING_INFO(ENTITY_TAMEABLE, 16, MetaType1_8.Byte, null), // todo, new index
    TAMING_OWNER(ENTITY_TAMEABLE, 17, MetaType1_8.String, null),

    // Wolf
    WOLF_HEALTH(WOLF, 18, MetaType1_8.Float, null), // todo, new index
    WOLF_BEGGING(WOLF, 19, MetaType1_8.Byte, null),
    WOLF_COLLAR(WOLF, 20, MetaType1_8.Byte, null),

    // Ocelot
    OCELOT_TYPE(OCELOT, 18, MetaType1_8.Byte, null), // todo, new index

    // Pig
    PIG_SADDLE(PIG, 16, MetaType1_8.Byte, null), // todo, new index

    // TODO: new index mappings, can we have anything in that enum?

    // Sheep
    SHEEP_COLOR(SHEEP, 16, MetaType1_8.Byte, null),

    // Villager
    VILLAGER_PROFESSION(VILLAGER, 16, MetaType1_8.Int, null),

    // Guardian
    GUARDIAN_INFO(GUARDIAN, 16, MetaType1_8.Int, null),
    GUARDIAN_TARGET(GUARDIAN, 17, MetaType1_8.Int, null),

    // Zombie
    ZOMBIE_IS_CHILD(ZOMBIE, 12, MetaType1_8.Byte, null),
    ZOMBIE_IS_VILLAGER(ZOMBIE, 13, MetaType1_8.Byte, null),
    ZOMBIE_IS_CONVERTING(ZOMBIE, 14, MetaType1_8.Byte, null),

    // Enderman
    ENDERMAN_BLOCKSTATE(ENDERMAN, 16, MetaType1_8.Short, null),
    ENDERMAN_BLOCKDATA(ENDERMAN, 17, MetaType1_8.Byte, null), // todo special check this
    ENDERMAN_IS_SCREAMING(ENDERMAN, 18, MetaType1_8.Byte, null),

    // Spider
    SPIDER_CIMBING(SPIDER, 16, MetaType1_8.Byte, null),

    // Creeper
    CREEPER_FUSE(CREEPER, 16, MetaType1_8.Byte, null),
    CREEPER_IS_POWERED(CREEPER, 17, MetaType1_8.Byte, null),
    CREEPER_IS_IGNITED(CREEPER, 18, MetaType1_8.Byte, null),

    // Skeleton
    SKELETON_TYPE(SKELETON, 13, MetaType1_8.Byte, null),

    // Witch
    WITCH_AGGRO(WITCH, 21, MetaType1_8.Byte, null),

    // Blaze
    BLAZE_ON_FIRE(BLAZE, 16, MetaType1_8.Byte, null),

    // Wither
    WITHER_TARGET1(WITHER, 17, MetaType1_8.Int, null),
    WITHER_TARGET2(WITHER, 18, MetaType1_8.Int, null),
    WITHER_TARGET3(WITHER, 19, MetaType1_8.Int, null),
    WITHER_INVULN_TIME(WITHER, 20, MetaType1_8.Int, null),

    // Iron golem
    IRON_PLAYER_MADE(IRON_GOLEM, 16, MetaType1_8.Byte, null),

    // Slime
    SLIME_SIZE(SLIME, 16, MetaType1_8.Byte, null),

    // Bat
    BAT_IS_HANGING(BAT, 16, MetaType1_8.Byte, null),

    // Ghast
    GHAST_IS_ATTACKING(GHAST, 16, MetaType1_8.Byte, null),

    // Armor Stand
    ARMOR_STAND_INFO(ARMOR_STAND, 10, MetaType1_8.Byte, null),
    ARMOR_STAND_HEAD_POS(ARMOR_STAND, 11, MetaType1_8.Rotation, null),
    ARMOR_STAND_BODY_POS(ARMOR_STAND, 12, MetaType1_8.Rotation, null),
    ARMOR_STAND_LEFT_ARM_POS(ARMOR_STAND, 13, MetaType1_8.Rotation, null),
    ARMOR_STAND_RIGHT_ARM_POS(ARMOR_STAND, 14, MetaType1_8.Rotation, null),
    ARMOR_STAND_LEFT_LEG_POS(ARMOR_STAND, 15, MetaType1_8.Rotation, null),
    ARMOR_STAND_RIGHT_LEG_POS(ARMOR_STAND, 16, MetaType1_8.Rotation, null),

    // Player
    ENTITY_PLAYER_HUMAN_BYTE(ENTITY_PLAYER, 16, MetaType1_8.Byte, null),
    ENTITY_PLAYER_ADDITIONAL_HEARTS(ENTITY_PLAYER, 17, MetaType1_8.Float, null),
    ENTITY_PLAYER_SCORE(ENTITY_PLAYER, 18, MetaType1_8.Int, null),
    ENTITY_PLAYER_SKIN_FLAGS(ENTITY_PLAYER, 10, MetaType1_8.Byte, null),

    // Wither skull
    WITHER_SKULL_INVULN(WITHER_SKULL, 10, MetaType1_8.Byte, null),

    // Arrow
    ARROW_IS_CRIT(ARROW, 16, MetaType1_8.Byte, null),

    // Ender crystal
    ENDER_CRYSTAL_HEALTH(ENDER_CRYSTAL, 8, MetaType1_8.Int, null), // null is correct here, throw away data....

    // Firework
    FIREWORK_ROCKET_INFO(FIREWORK_ROCKET, 8, MetaType1_8.Slot, null), // TODO, what does addByType?

    // Boat
    BOAT_SINCE_HIT(BOAT, 17, MetaType1_8.Int, null),
    BOAT_FORWARD_DIRECTION(BOAT, 18, MetaType1_8.Int, null),
    BOAT_DAMAGE_TAKEN(BOAT, 19, MetaType1_8.Float, null),

    // Dropped item
    ITEM_ITEM(ITEM, 10, MetaType1_8.Slot, null), // TODO, what does addByType?

    // Minecarts
    MINECART_ABSTRACT_SHAKING_POWER(MINECART_ABSTRACT, 17, MetaType1_8.Int, null),
    MINECART_ABSTRACT_SHAKING_DIRECTION(MINECART_ABSTRACT, 18, MetaType1_8.Int, null),
    MINECART_ABSTRACT_DAMAGE_TAKEN(MINECART_ABSTRACT, 19, MetaType1_8.Float, null),
    MINECART_ABSTRACT_BLOCK(MINECART_ABSTRACT, 20, MetaType1_8.Int, null),
    MINECART_ABSTRACT_BLOCK_Y(MINECART_ABSTRACT, 21, MetaType1_8.Int, null),
    MINECART_ABSTRACT_SHOW_BLOCK(MINECART_ABSTRACT, 22, MetaType1_8.Byte, null),

    MINECART_COMMAND_BLOCK_COMMAND(MINECART_COMMAND_BLOCK, 23, MetaType1_8.String, null),
    MINECART_COMMAND_BLOCK_COMMAND_OUTPUT(MINECART_COMMAND_BLOCK, 24, MetaType1_8.String, null),

    MINECART_FURNACE_IS_POWERED(MINECART_FURNACE, 16, MetaType1_8.Byte, null),

    // Item frame
    ITEM_FRAME_ITEM(ITEM_FRAME, 8, MetaType1_8.Slot, null), // TODO, what does addByType?
    ITEM_FRAME_ROTATION(ITEM_FRAME, 9, MetaType1_8.Byte, null);

    private static final HashMap<Pair<EntityTypes1_8.EntityType, Integer>, MetaIndex> metadataRewrites = new HashMap<>();

    static {
        for (MetaIndex index : MetaIndex.values())
            metadataRewrites.put(new Pair<>(index.clazz, index.index), index);
    }

    private final EntityTypes1_8.EntityType clazz;
    private final int newIndex;
    private final MetaType1_9 newType;
    private final MetaType1_8 oldType;
    private final int index;

    MetaIndex(EntityTypes1_8.EntityType type, int index, MetaType1_8 oldType, @Nullable MetaType1_9 newType) {
        this.clazz = type;
        this.index = index;
        this.newIndex = index;
        this.oldType = oldType;
        this.newType = newType;
    }

    MetaIndex(EntityTypes1_8.EntityType type, int index, MetaType1_8 oldType, int newIndex, @Nullable MetaType1_9 newType) {
        this.clazz = type;
        this.index = index;
        this.oldType = oldType;
        this.newIndex = newIndex;
        this.newType = newType;
    }

    public EntityTypes1_8.EntityType getClazz() {
        return clazz;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public @Nullable MetaType1_9 getNewType() {
        return newType;
    }

    public MetaType1_8 getOldType() {
        return oldType;
    }

    public int getIndex() {
        return index;
    }

    private static Optional<MetaIndex> getIndex(EntityType type, int index) {
        Pair pair = new Pair<>(type, index);
        return Optional.ofNullable(metadataRewrites.get(pair));
    }

    public static MetaIndex searchIndex(EntityType type, int index) {
        EntityType currentType = type;
        do {
            Optional<MetaIndex> optMeta = getIndex(currentType, index);

            if (optMeta.isPresent()) {
                return optMeta.get();
            }

            currentType = currentType.getParent();
        } while (currentType != null);

        return null;
    }

}