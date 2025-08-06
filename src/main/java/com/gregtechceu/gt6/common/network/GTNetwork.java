package com.gregtechceu.gt6.common.network;

import com.gregtechceu.gt6.GTCEu;
import com.gregtechceu.gt6.common.network.packets.*;
import com.gregtechceu.gt6.common.network.packets.hazard.SPacketAddHazardZone;
import com.gregtechceu.gt6.common.network.packets.hazard.SPacketRemoveHazardZone;
import com.gregtechceu.gt6.common.network.packets.hazard.SPacketSyncHazardZoneStrength;
import com.gregtechceu.gt6.common.network.packets.hazard.SPacketSyncLevelHazards;
import com.gregtechceu.gt6.common.network.packets.prospecting.SPacketProspectBedrockFluid;
import com.gregtechceu.gt6.common.network.packets.prospecting.SPacketProspectBedrockOre;
import com.gregtechceu.gt6.common.network.packets.prospecting.SPacketProspectOre;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Function;

public class GTNetwork {

    private static final String PROTOCOL_VERSION = "1.0.0";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(GTCEu.id("network"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int nextPacketId = 0;

    public static void sendToServer(INetPacket packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void sendToPlayersInLevel(ResourceKey<Level> level, INetPacket packet) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> level), packet);
    }

    public static void sendToPlayersNearPoint(PacketDistributor.TargetPoint point, INetPacket packet) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> point), packet);
    }

    public static void sendToAllPlayersTrackingEntity(Entity entity, boolean includeSelf, INetPacket packet) {
        INSTANCE.send(includeSelf ? PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity) :
                PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    public static void sendToAllPlayersTrackingChunk(LevelChunk chunk, INetPacket packet) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
    }

    public static void sendToAll(INetPacket packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToPlayer(ServerPlayer player, INetPacket packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void reply(NetworkEvent.Context context, INetPacket packet) {
        INSTANCE.reply(packet, context);
    }

    public interface INetPacket {

        void encode(FriendlyByteBuf buffer);

        void execute(NetworkEvent.Context context);
    }

    public static <T extends INetPacket> void register(Class<T> cls, Function<FriendlyByteBuf, T> decode,
                                                       NetworkDirection direction) {
        INSTANCE.registerMessage(nextPacketId++, cls, INetPacket::encode, decode, (msg, ctx) -> {
            ctx.get().enqueueWork(() -> msg.execute(ctx.get()));
            ctx.get().setPacketHandled(true);
        }, Optional.ofNullable(direction));
    }

    public static void init() {
        register(SCPacketMonitorGroupNBTChange.class, SCPacketMonitorGroupNBTChange::new, null);
        register(CPacketImageRequest.class, CPacketImageRequest::new, NetworkDirection.PLAY_TO_SERVER);
        register(SPacketImageResponse.class, SPacketImageResponse::new, NetworkDirection.PLAY_TO_CLIENT);

        register(CPacketKeysPressed.class, CPacketKeysPressed::new, NetworkDirection.PLAY_TO_SERVER);
        register(CPacketKeyDown.class, CPacketKeyDown::new, NetworkDirection.PLAY_TO_SERVER);

        register(SPacketSyncOreVeins.class, SPacketSyncOreVeins::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketSyncFluidVeins.class, SPacketSyncFluidVeins::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketSyncBedrockOreVeins.class, SPacketSyncBedrockOreVeins::new, NetworkDirection.PLAY_TO_CLIENT);

        register(SPacketAddHazardZone.class, SPacketAddHazardZone::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketRemoveHazardZone.class, SPacketRemoveHazardZone::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketSyncHazardZoneStrength.class, SPacketSyncHazardZoneStrength::new,
                NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketSyncLevelHazards.class, SPacketSyncLevelHazards::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketProspectOre.class, SPacketProspectOre::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketProspectBedrockOre.class, SPacketProspectBedrockOre::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketProspectBedrockFluid.class, SPacketProspectBedrockFluid::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketSendWorldID.class, SPacketSendWorldID::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SPacketNotifyCapeChange.class, SPacketNotifyCapeChange::new, NetworkDirection.PLAY_TO_CLIENT);
        register(SCPacketShareProspection.class, SCPacketShareProspection::new, null);
    }
}
