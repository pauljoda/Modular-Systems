package com.pauljoda.modularsystems.core.network.bidirectional;


import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import com.pauljoda.nucleus.network.PacketManager;
import com.pauljoda.nucleus.network.packets.ClientBoundPacket;
import com.pauljoda.nucleus.network.packets.ServerBoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * A packet that syncs a field between client and server.
 */
public record SyncableFieldPacket(boolean returnValue, int fieldId, double value, BlockPos blockPosition)
        implements ClientBoundPacket, ServerBoundPacket {

    /*******************************************************************************************************************
     * Encode/Decode                                                                                                   *
     *******************************************************************************************************************/

    public static SyncableFieldPacket decode(FriendlyByteBuf buf) {
        var returnValue = buf.readBoolean();
        var fieldID = buf.readInt();
        var value = buf.readDouble();
        var blockPosition = BlockPos.of(buf.readLong());
        return new SyncableFieldPacket(returnValue, fieldID, value, blockPosition);
    }

    /**
     * Write to buffer
     *
     * @param buf
     */
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(returnValue);
        buf.writeInt(fieldId);
        buf.writeDouble(value);
        buf.writeLong(blockPosition.asLong());
    }

    /*******************************************************************************************************************
     * Handle Packet                                                                                                   *
     *******************************************************************************************************************/


    /**
     * Handles the packet on the client side.
     *
     * @param player The player that received the packet.
     */
    @Override
    public void handleOnClient(Player player) {
        Level level = player.level();

        // Safety check
        if (blockPosition == null ||
                level.getBlockEntity(blockPosition) == null ||
                !(level.getBlockEntity(blockPosition) instanceof Syncable))
            return;

        // If wanting to ping back server for whatever reason
        if (returnValue)
            PacketManager.INSTANCE.sendToServer(new com.pauljoda.nucleus.network.packets.bidirectional.SyncableFieldPacket(false, fieldId,
                    ((Syncable) level.getBlockEntity(blockPosition)).getVariable(fieldId), blockPosition));
        else
            ((Syncable) level.getBlockEntity(blockPosition)).setVariable(fieldId, value);

    }

    /**
     * Handles the packet on the server side.
     *
     * @param player The server player that received the packet.
     */
    @Override
    public void handleOnServer(ServerPlayer player) {
        Level level = player.level();

        // Safety check for non syncable tiles
        if (level.getBlockEntity(blockPosition) == null ||
                !(level.getBlockEntity(blockPosition) instanceof Syncable))
            return;

        // If true, other client wanted all around to see value change
        if (returnValue)
            PacketManager.INSTANCE.sendToAllAround(
                    new SyncableFieldPacket(false, fieldId,
                            ((Syncable) level.getBlockEntity(blockPosition)).getVariable(fieldId), blockPosition),
                    new PacketDistributor.TargetPoint(
                            blockPosition.getX(),
                            blockPosition.getY(),
                            blockPosition.getZ(),
                            25,
                            level.dimension()));
        else // On server update
            ((Syncable) level.getBlockEntity(blockPosition)).setVariable(fieldId, value);
    }
}