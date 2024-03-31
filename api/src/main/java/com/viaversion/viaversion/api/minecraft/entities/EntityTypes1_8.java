/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2024 ViaVersion and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.util.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// 1.8 Entity / Object ids
public class EntityTypes1_8 {

    public static EntityType getEntityType(final int id, @Nullable Integer data, boolean object) {
        if (object) {
            return ObjectType.getObjectEntity(id, data).map(ObjectType::getType).orElse(null);
        } else {
            return EntityType.getEntity(id).orElse(null);
        }
    }

    public enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
        ENTITY(-1),

        // Objects
        FISH_HOOK(-1, ENTITY),

        ENDER_EYE(15, ENTITY),
        ITEM(1, ENTITY),

        // Minecarts
        MINECART_ABSTRACT(-1, ENTITY),

        MINECART_CONTAINER(-1, MINECART_ABSTRACT),

        MINECART_CHEST(43, MINECART_CONTAINER),
        MINECART_HOPPER(46, MINECART_CONTAINER),

        MINECART_COMMAND_BLOCK(40, MINECART_ABSTRACT),
        MINECART_TNT(45, MINECART_ABSTRACT),
        MINECART_MOB_SPAWNER(47, MINECART_ABSTRACT),
        MINECART_EMPTY(42, MINECART_ABSTRACT),
        MINECART_FURNACE(44, MINECART_ABSTRACT),

        // Hanging
        ENTITY_HANGING(-1, ENTITY),

        ITEM_FRAME(18, ENTITY_HANGING),
        LEASH_KNOT(8, ENTITY_HANGING),
        PAINTING(9, ENTITY_HANGING),

        ENTITY_LIVING_BASE(-1, ENTITY),
        ENTITY_LIVING(48, ENTITY_LIVING_BASE),
        ENTITY_CREATURE(-1, ENTITY_LIVING),
        ENTITY_AGEABLE(-1, ENTITY_CREATURE),

        ENTITY_ANIMAL(-1, ENTITY_AGEABLE),

        HORSE(100, ENTITY_ANIMAL),

        COW(92, ENTITY_ANIMAL),
        MOOSHROOM(96, COW),

        CHICKEN(93, ENTITY_ANIMAL),
        RABBIT(101, ENTITY_ANIMAL),

        ENTITY_TAMEABLE(-1, ENTITY_ANIMAL),

        WOLF(95, ENTITY_TAMEABLE),
        OCELOT(98, ENTITY_TAMEABLE),

        PIG(90, ENTITY_ANIMAL),
        SHEEP(91, ENTITY_ANIMAL),

        VILLAGER(120, ENTITY_AGEABLE),

        ENTITY_MOB(49, ENTITY_CREATURE),

        GUARDIAN(68, ENTITY_MOB),

        ZOMBIE(54, ENTITY_MOB),
        PIG_ZOMBIE(57, ZOMBIE),

        ENDERMAN(58, ENTITY_MOB),

        SPIDER(52, ENTITY_MOB),
        CAVE_SPIDER(59, SPIDER),

        GIANT_ZOMBIE(53, ENTITY_MOB),
        ENDERMITE(67, ENTITY_MOB),
        CREEPER(50, ENTITY_MOB),
        SILVERFISH(60, ENTITY_MOB),
        SKELETON(51, ENTITY_MOB),
        WITCH(66, ENTITY_MOB),
        BLAZE(61, ENTITY_MOB),
        WITHER(64, ENTITY_MOB),

        GOLEM(-1, ENTITY_CREATURE),

        SNOWMAN(97, GOLEM),
        IRON_GOLEM(99, GOLEM),

        ENDER_DRAGON(63, ENTITY_LIVING),

        SLIIME(55, ENTITY_LIVING),
        MAGMA_CUBE(62, SLIIME),

        ENTITY_WATER_MOB(-1, ENTITY_LIVING),
        SQUID(94, ENTITY_WATER_MOB),

        ENTITY_AMBIENT_CREATURE(-1, ENTITY_LIVING),
        BAT(65, ENTITY_AMBIENT_CREATURE),

        ENTITY_FLYING(-1, ENTITY_LIVING),
        GHAST(56, ENTITY_FLYING),

        ARMOR_STAND(30, ENTITY_LIVING),

        FIREBALL(-1, ENTITY),
        LARGE_FIREBALL(12, FIREBALL),
        SMALL_FIREBALL(13, FIREBALL),
        WITHER_SKULL(19, FIREBALL),

        ENTITY_THROWABLE(-1, ENTITY),
        POTION(16, ENTITY_THROWABLE),
        SNOWBALL(11, ENTITY_THROWABLE),
        EXP_BOTTLE(17, ENTITY_THROWABLE),
        ENDER_PEARL(14, ENTITY_THROWABLE),
        EGG(7, ENTITY_THROWABLE),

        ARROW(10, ENTITY),
        ENDER_CRYSTAL(200, ENTITY),
        XP_ORB(2, ENTITY),
        FIREWORK_ROCKET(22, ENTITY),
        FALLING_BLOCK(21, ENTITY),
        TNT_PRIMED(20, ENTITY),
        BOAT(41, ENTITY);

        private static final Map<Integer, EntityType> TYPES = new HashMap<>();

        private final int id;
        private final EntityType parent;

        EntityType(int id) {
            this.id = id;
            this.parent = null;
        }

        EntityType(int id, EntityType parent) {
            this.id = id;
            this.parent = parent;
        }

        static {
            for (EntityType type : EntityType.values()) {
                TYPES.put(type.id, type);
            }
        }

        public static Optional<EntityType> getEntity(final int id) {
            if (id == -1) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(TYPES.get(id));
            }
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public EntityType getParent() {
            return parent;
        }

        @Override
        public String identifier() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAbstractType() {
            return id != -1;
        }
    }

    public enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType {
        MINECART_RIDEABLE(10, 0, EntityType.MINECART_EMPTY),
        MINECART_CHEST(10, 1, EntityType.MINECART_CHEST),
        MINECRAFT_FURNACE(10, 2, EntityType.MINECART_FURNACE),
        MINECART_TNT(10, 3, EntityType.MINECART_TNT),
        MINECART_SPAWNER(10, 4, EntityType.MINECART_MOB_SPAWNER),
        MINECART_HOPPER(10, 5, EntityType.MINECART_HOPPER),
        MINECART_COMMAND_BLOCK(10, 6, EntityType.MINECART_COMMAND_BLOCK),
        FISHING_HOOK(90, EntityType.FISH_HOOK), // TODO, use entity tracker for this one
        ARROW(60, EntityType.ARROW),
        SNOWBALL(61, EntityType.SNOWBALL),
        ITEM_FRAME(71, EntityType.ITEM_FRAME),
        LEASH_KNOT(77, EntityType.LEASH_KNOT),
        ENDER_PEARL(65, EntityType.ENDER_PEARL),
        ENDER_EYE(72, EntityType.ENDER_EYE),
        FIREWORK_ROCKET(76, EntityType.FIREWORK_ROCKET),
        LARGE_FIREBALL(63, EntityType.LARGE_FIREBALL),
        SMALL_FIREBALL(64, EntityType.SMALL_FIREBALL),
        WITHER_SKULL(66, EntityType.WITHER_SKULL),
        EGG(62, EntityType.EGG),
        POTION(73, EntityType.POTION),
        EXP_BOTTLE(75, EntityType.EXP_BOTTLE),
        BOAT(1, EntityType.BOAT),
        TNT_PRIMED(50, EntityType.TNT_PRIMED),
        ARMOR_STAND(78, EntityType.ARMOR_STAND),
        ENDER_CRYSTAL(51, EntityType.ENDER_CRYSTAL),
        ITEM(2, EntityType.ITEM),
        FALLING_BLOCK(70, EntityType.FALLING_BLOCK);

        private static final Map<Pair<Integer, Integer>, ObjectType> TYPES = new HashMap<>();

        private final int id;
        private int data;

        private final EntityType type;

        static {
            for (ObjectType type : ObjectType.values()) {
                TYPES.put(new Pair<>(type.id, type.data), type);
            }
        }

        ObjectType(int id, EntityType type) {
            this.id = id;
            this.type = type;
        }

        ObjectType(int id, int data, EntityType type) {
            this.id = id;
            this.data = data;
            this.type = type;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public EntityType getType() {
            return type;
        }

        public static Optional<ObjectType> getObjectEntity(final int id, @Nullable Integer data) {
            if (id == -1) {
                return Optional.empty();
            } else {
                if (data == null) {
                    data = 0;
                }
                return Optional.ofNullable(TYPES.get(new Pair<>(id, data)));
            }
        }
    }
}
