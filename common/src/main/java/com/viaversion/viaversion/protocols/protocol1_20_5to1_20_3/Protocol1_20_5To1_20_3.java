/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2023 ViaVersion and contributors
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
package com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.data.StructuredDataKey;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_20_5;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPacket1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ServerboundPacket1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ClientboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ClientboundPackets1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ServerboundConfigurationPackets1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ServerboundPacket1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.packet.ServerboundPackets1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.rewriter.BlockItemPacketRewriter1_20_5;
import com.viaversion.viaversion.protocols.protocol1_20_5to1_20_3.rewriter.EntityPacketRewriter1_20_5;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

import static com.viaversion.viaversion.util.ProtocolUtil.packetTypeMap;

public final class Protocol1_20_5To1_20_3 extends AbstractProtocol<ClientboundPacket1_20_3, ClientboundPacket1_20_5, ServerboundPacket1_20_3, ServerboundPacket1_20_5> {

    public static final MappingData MAPPINGS = new MappingDataBase("1.20.3", "1.20.5");
    private final EntityPacketRewriter1_20_5 entityRewriter = new EntityPacketRewriter1_20_5(this);
    private final BlockItemPacketRewriter1_20_5 itemRewriter = new BlockItemPacketRewriter1_20_5(this);
    private final TagRewriter<ClientboundPacket1_20_3> tagRewriter = new TagRewriter<>(this);

    public Protocol1_20_5To1_20_3() {
        super(ClientboundPacket1_20_3.class, ClientboundPacket1_20_5.class, ServerboundPacket1_20_3.class, ServerboundPacket1_20_5.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        tagRewriter.registerGeneric(ClientboundPackets1_20_3.TAGS);
        tagRewriter.registerGeneric(ClientboundConfigurationPackets1_20_3.UPDATE_TAGS);

        final SoundRewriter<ClientboundPacket1_20_3> soundRewriter = new SoundRewriter<>(this);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_20_3.SOUND);
        soundRewriter.register1_19_3Sound(ClientboundPackets1_20_3.ENTITY_SOUND);

        new StatisticsRewriter<>(this).register(ClientboundPackets1_20_3.STATISTICS);

        registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO, wrapper -> {
            wrapper.passthrough(Type.STRING); // Server ID
            wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE); // Public key
            wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE); // Challenge
            wrapper.write(Type.BOOLEAN, true); // Authenticate
        });

        registerClientbound(ClientboundPackets1_20_3.SERVER_DATA, wrapper -> {
            wrapper.passthrough(Type.TAG); // MOTD
            wrapper.passthrough(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE); // Icon
            wrapper.read(Type.BOOLEAN); // Enforces secure chat - moved to join game
        });

        cancelServerbound(State.LOGIN, ServerboundLoginPackets.COOKIE_RESPONSE.getId());
        cancelServerbound(ServerboundConfigurationPackets1_20_5.COOKIE_RESPONSE);
        cancelServerbound(ServerboundConfigurationPackets1_20_5.SELECT_KNOWN_PACKS);
        cancelServerbound(ServerboundPackets1_20_5.COOKIE_RESPONSE);
        cancelServerbound(ServerboundPackets1_20_5.DEBUG_SAMPLE_SUBSCRIPTION);
    }

    @Override
    protected void onMappingDataLoaded() {
        super.onMappingDataLoaded();

        EntityTypes1_20_5.initialize(this);
        Types1_20_5.PARTICLE.filler(this)
            .reader("block", ParticleType.Readers.BLOCK)
            .reader("block_marker", ParticleType.Readers.BLOCK)
            .reader("dust", ParticleType.Readers.DUST)
            .reader("falling_dust", ParticleType.Readers.BLOCK)
            .reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION)
            .reader("item", ParticleType.Readers.ITEM1_20_2)
            .reader("vibration", ParticleType.Readers.VIBRATION1_20_3)
            .reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE)
            .reader("shriek", ParticleType.Readers.SHRIEK);
        Types1_20_5.STRUCTURED_DATA.filler(this)
            .add(StructuredDataKey.CUSTOM_DATA)
            .add(StructuredDataKey.DAMAGE)
            .add(StructuredDataKey.UNBREAKABLE)
            .add(StructuredDataKey.CUSTOM_NAME)
            .add(StructuredDataKey.LORE)
            .add(StructuredDataKey.ENCHANTMENTS)
            .add(StructuredDataKey.CAN_PLACE_ON)
            .add(StructuredDataKey.CAN_BREAK)
            .add(StructuredDataKey.ATTRIBUTE_MODIFIERS)
            .add(StructuredDataKey.CUSTOM_MODEL_DATA)
            .add(StructuredDataKey.HIDE_ADDITIONAL_TOOLTIP)
            .add(StructuredDataKey.REPAIR_COST)
            .add(StructuredDataKey.CREATIVE_SLOT_LOCK)
            .add(StructuredDataKey.ENCHANTMENT_GLINT_OVERRIDE)
            .add(StructuredDataKey.INTANGIBLE_PROJECTILE)
            .add(StructuredDataKey.STORED_ENCHANTMENTS)
            .add(StructuredDataKey.DYED_COLOR)
            .add(StructuredDataKey.MAP_COLOR)
            .add(StructuredDataKey.MAP_ID)
            .add(StructuredDataKey.MAP_DECORATIONS)
            .add(StructuredDataKey.MAP_POST_PROCESSING)
            .add(StructuredDataKey.CHARGED_PROJECTILES)
            .add(StructuredDataKey.BUNDLE_CONTENTS)
            .add(StructuredDataKey.POTION_CONTENTS)
            .add(StructuredDataKey.SUSPICIOUS_STEW_EFFECTS)
            .add(StructuredDataKey.WRITABLE_BOOK_CONTENT)
            .add(StructuredDataKey.WRITTEN_BOOK_CONTENT)
            .add(StructuredDataKey.TRIM)
            .add(StructuredDataKey.DEBUG_STICK_STATE)
            .add(StructuredDataKey.ENTITY_DATA)
            .add(StructuredDataKey.BUCKET_ENTITY_DATA)
            .add(StructuredDataKey.BLOCK_ENTITY_DATA)
            .add(StructuredDataKey.INSTRUMENT)
            .add(StructuredDataKey.RECIPES)
            .add(StructuredDataKey.LODESTONE_TARGET)
            .add(StructuredDataKey.FIREWORK_EXPLOSION)
            .add(StructuredDataKey.FIREWORKS)
            .add(StructuredDataKey.PROFILE)
            .add(StructuredDataKey.NOTE_BLOCK_SOUND)
            .add(StructuredDataKey.BANNER_PATTERNS)
            .add(StructuredDataKey.BASE_COLOR)
            .add(StructuredDataKey.POT_DECORATIONS)
            .add(StructuredDataKey.CONTAINER)
            .add(StructuredDataKey.BLOCK_STATE)
            .add(StructuredDataKey.BEES)
            .add(StructuredDataKey.LOCK)
            .add(StructuredDataKey.CONTAINER_LOOT);

        tagRewriter.addTag(RegistryType.ITEM, "minecraft:dyeable", 853, 854, 855, 856, 1120);
    }

    @Override
    public void init(final UserConnection connection) {
        addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_5.PLAYER));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    @Override
    public EntityPacketRewriter1_20_5 getEntityRewriter() {
        return entityRewriter;
    }

    @Override
    public BlockItemPacketRewriter1_20_5 getItemRewriter() {
        return itemRewriter;
    }

    @Override
    protected PacketTypesProvider<ClientboundPacket1_20_3, ClientboundPacket1_20_5, ServerboundPacket1_20_3, ServerboundPacket1_20_5> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<>(
            packetTypeMap(unmappedClientboundPacketType, ClientboundPackets1_20_3.class, ClientboundConfigurationPackets1_20_3.class),
            packetTypeMap(mappedClientboundPacketType, ClientboundPackets1_20_5.class, ClientboundConfigurationPackets1_20_5.class),
            packetTypeMap(mappedServerboundPacketType, ServerboundPackets1_20_3.class, ServerboundConfigurationPackets1_20_2.class),
            packetTypeMap(unmappedServerboundPacketType, ServerboundPackets1_20_5.class, ServerboundConfigurationPackets1_20_5.class)
        );
    }
}