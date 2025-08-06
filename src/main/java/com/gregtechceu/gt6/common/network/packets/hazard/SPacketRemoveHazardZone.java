package com.gregtechceu.gt6.common.network.packets.hazard;

import com.gregtechceu.gt6.client.EnvironmentalHazardClientHandler;
import com.gregtechceu.gt6.common.network.GTNetwork;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SPacketRemoveHazardZone implements GTNetwork.INetPacket {

    public ChunkPos pos;

    public SPacketRemoveHazardZone(FriendlyByteBuf buf) {
        pos = buf.readChunkPos();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeChunkPos(pos);
    }

    @Override
    public void execute(NetworkEvent.Context context) {
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            EnvironmentalHazardClientHandler.INSTANCE.removeHazardZone(pos);
        }
    }
}
