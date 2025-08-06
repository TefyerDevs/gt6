package com.gregtechceu.gt6.common.network.packets;

import com.gregtechceu.gt6.common.capability.WorldIDSaveData;
import com.gregtechceu.gt6.common.network.GTNetwork;
import com.gregtechceu.gt6.integration.map.ClientCacheManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SPacketSendWorldID implements GTNetwork.INetPacket {

    private String worldId;

    public SPacketSendWorldID(FriendlyByteBuf buf) {
        worldId = buf.readUtf();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(WorldIDSaveData.getWorldID());
    }

    @Override
    public void execute(NetworkEvent.Context context) {
        ClientCacheManager.init(worldId);
    }
}
