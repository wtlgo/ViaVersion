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

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// 1.8 Entity / Object ids
public class EntityTypes1_8 {

    public static EntityType getTypeFromId(int typeID, boolean isObject) {
        Optional<EntityType> type;

        if (isObject)
            type = ObjectType.getPCEntity(typeID);
        else
            type = EntityType.findById(typeID);

        if (!type.isPresent()) {
            Via.getPlatform().getLogger().severe("Could not find 1.8 type id " + typeID + " isObject=" + isObject);
            return EntityType.ENTITY; // Fall back to the basic ENTITY
        }

        return type.get();
    }

    public enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType {
        ENTITY(-1),

        // Objects
        ENTITY_FISH_HOOK(-1, ENTITY),

        ENTITY_ENDER_EYE(15, ENTITY),
        ENTITY_ITEM(1, ENTITY),

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

        ENTITY_ITEM_FRAME(18, ENTITY_HANGING),
        ENTITY_LEASH_KNOT(8, ENTITY_HANGING),
        ENTITY_PAINTING(9, ENTITY_HANGING),

        ENTITY_LIVING_BASE(-1, ENTITY),
        ENTITY_LIVING(48, ENTITY_LIVING_BASE),
        ENTITY_CREATURE(-1, ENTITY_LIVING),
        ENTITY_AGEABLE(-1, ENTITY_CREATURE),

        ENTITY_ANIMAL(-1, ENTITY_AGEABLE),

        ENTITY_HORSE(100, ENTITY_ANIMAL),

        ENTITY_COW(92, ENTITY_ANIMAL),
        ENTITY_MOOSHROOM(96, ENTITY_COW),

        ENTITY_CHICKEN(93, ENTITY_ANIMAL),
        ENTITY_RABBIT(101, ENTITY_ANIMAL),

        ENTITY_TAMEABLE(-1, ENTITY_ANIMAL),

        ENTITY_WOLF(95, ENTITY_TAMEABLE),
        ENTITY_OCELOT(98, ENTITY_TAMEABLE),

        ENTITY_PIG(90, ENTITY_ANIMAL),
        ENTITY_SHEEP(91, ENTITY_ANIMAL),

        ENTITY_VILLAGER(120, ENTITY_AGEABLE),

        ENTITY_MOB(49, ENTITY_CREATURE),

        ENTITY_GUARDIAN(68, ENTITY_MOB),

        ENTITY_ZOMBIE(54, ENTITY_MOB),
        ENTITY_PIG_ZOMBIE(57, ENTITY_ZOMBIE),

        ENTITY_ENDERMAN(58, ENTITY_MOB),

        ENTITY_SPIDER(52, ENTITY_MOB),
        ENTITY_CAVE_SPIDER(59, ENTITY_SPIDER),

        ENTITY_GIANT_ZOMBIE(53, ENTITY_MOB),
        ENTITY_ENDERMITE(67, ENTITY_MOB),
        ENTITY_CREEPER(50, ENTITY_MOB),
        ENTITY_SILVERFISH(60, ENTITY_MOB),
        ENTITY_SKELETON(51, ENTITY_MOB),
        ENTITY_WITCH(66, ENTITY_MOB),
        ENTITY_BLAZE(61, ENTITY_MOB),
        ENTITY_WITHER(64, ENTITY_MOB),

        ENTITY_GOLEM(-1, ENTITY_CREATURE),

        ENTITY_SNOWMAN(97, ENTITY_GOLEM),
        ENTITY_IRON_GOLEM(99, ENTITY_GOLEM),

        ENTITY_DRAGON(63, ENTITY_LIVING),

        ENTITY_SLIIME(55, ENTITY_LIVING),
        ENTITY_MAGMA_CUBE(62, ENTITY_SLIIME),

        ENTITY_WATER_MOB(-1, ENTITY_LIVING),
        ENTITY_SQUID(94, ENTITY_WATER_MOB),

        ENTITY_AMBIENT_CREATURE(-1, ENTITY_LIVING),
        ENTITY_BAT(65, ENTITY_AMBIENT_CREATURE),

        ENTITY_FLYING(-1, ENTITY_LIVING),
        ENTITY_GHAST(56, ENTITY_FLYING),

        ENTITY_ARMOR_STAND(30, ENTITY_LIVING),

        ENTITY_FIREBALL(-1, ENTITY),
        ENTITY_LARGE_FIREBALL(12, ENTITY_FIREBALL),
        ENTITY_SMALL_FIREBALL(13, ENTITY_FIREBALL),
        ENTITY_WITHER_SKULL(19, ENTITY_FIREBALL),

        ENTITY_THROWABLE(-1, ENTITY),
        ENTITY_POTION(16, ENTITY_THROWABLE),
        ENTITY_SNOWBALL(11, ENTITY_THROWABLE),
        ENTITY_EXP_BOTTLE(17, ENTITY_THROWABLE),
        ENTITY_ENDER_PEARL(14, ENTITY_THROWABLE),
        ENTITY_EGG(7, ENTITY_THROWABLE),

        ENTITY_ARROW(10, ENTITY),
        ENTITY_ENDER_CRYSTAL(200, ENTITY),
        ENTITY_XP_ORB(2, ENTITY),
        ENTITY_FIREWORK_ROCKET(22, ENTITY),
        ENTITY_FALLING_BLOCK(21, ENTITY),
        ENTITY_TNT_PRIMED(20, ENTITY),
        ENTITY_BOAT(41, ENTITY);

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

        public static Optional<EntityType> findById(int id) {
            if (id == -1)  // Check if this is called
                return Optional.empty();
            return Optional.ofNullable(TYPES.get(id));
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
        FISHING_HOOK(90, EntityType.ENTITY_FISH_HOOK), // TODO, use entity tracker for this one
        ARROW(60, EntityType.ENTITY_ARROW),
        SNOWBALL(61, EntityType.ENTITY_SNOWBALL),
        ITEM_FRAME(71, EntityType.ENTITY_ITEM_FRAME),
        LEASH_KNOT(77, EntityType.ENTITY_LEASH_KNOT),
        ENDER_PEARL(65, EntityType.ENTITY_ENDER_PEARL),
        ENDER_EYE(72, EntityType.ENTITY_ENDER_EYE),
        FIREWORK_ROCKET(76, EntityType.ENTITY_FIREWORK_ROCKET),
        LARGE_FIREBALL(63, EntityType.ENTITY_LARGE_FIREBALL),
        SMALL_FIREBALL(64, EntityType.ENTITY_SMALL_FIREBALL),
        WITHER_SKULL(66, EntityType.ENTITY_WITHER_SKULL),
        EGG(62, EntityType.ENTITY_EGG),
        POTION(73, EntityType.ENTITY_POTION),
        EXP_BOTTLE(75, EntityType.ENTITY_EXP_BOTTLE),
        BOAT(1, EntityType.ENTITY_BOAT),
        TNT_PRIMED(50, EntityType.ENTITY_TNT_PRIMED),
        ARMOR_STAND(78, EntityType.ENTITY_ARMOR_STAND),
        ENDER_CRYSTAL(51, EntityType.ENTITY_ENDER_CRYSTAL),
        ITEM(2, EntityType.ENTITY_ITEM),
        FALLING_BLOCK(70, EntityType.ENTITY_FALLING_BLOCK);

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

        public static Optional<ObjectType> findById(int id) {
            if (id == -1)
                return Optional.empty();
            return Optional.ofNullable(TYPES.get(id));
        }

        public static Optional<EntityType> getPCEntity(int id) {
            Optional<ObjectType> output = findById(id);

            if (!output.isPresent())
                return Optional.empty();
            return Optional.of(output.get().type);
        }
    }
}
