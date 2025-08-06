package com.gregtechceu.gt6.common.network.packets.hazard;

import com.gregtechceu.gt6.client.EnvironmentalHazardClientHandler;
import com.gregtechceu.gt6.common.capability.EnvironmentalHazardSavedData;
import com.gregtechceu.gt6.common.network.GTNetwork;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SPacketAddHazardZone implements GTNetwork.INetPacket {

    private ChunkPos pos;
    private EnvironmentalHazardSavedData.HazardZone zone;

    public SPacketAddHazardZone(FriendlyByteBuf buf) {
        pos = buf.readChunkPos();
        zone = EnvironmentalHazardSavedData.HazardZone.fromNetwork(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeChunkPos(pos);
        zone.toNetwork(buf);
    }

    @Override
    public void execute(NetworkEvent.Context context) {
        if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            EnvironmentalHazardClientHandler.INSTANCE.addHazardZone(pos, zone);
        }
    }
}
