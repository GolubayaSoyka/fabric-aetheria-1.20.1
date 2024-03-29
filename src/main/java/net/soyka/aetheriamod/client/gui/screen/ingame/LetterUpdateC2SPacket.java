package net.soyka.aetheriamod.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import java.util.List;
import java.util.Optional;

public class LetterUpdateC2SPacket extends BookUpdateC2SPacket
        implements Packet<ServerPlayPacketListener> {
    public static final int field_34038 = 4;
    private static final int MAX_TITLE_LENGTH = 128;
    private static final int MAX_PAGE_LENGTH = 8192;
    private static final int MAX_PAGES = 200;
    private final int slot;
    private final List<String> pages;
    private final Optional<String> title;

    public LetterUpdateC2SPacket(int slot, List<String> pages, Optional<String> title) {
        this.slot = slot;
        this.pages = ImmutableList.copyOf(pages);
        this.title = title;
    }

    public LetterUpdateC2SPacket(PacketByteBuf buf) {
        this.slot = buf.readVarInt();
        this.pages = buf.readCollection(PacketByteBuf.getMaxValidator(Lists::newArrayListWithCapacity, 200), buf2 -> buf2.readString(8192));
        this.title = buf.readOptional(buf2 -> buf2.readString(128));
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.slot);
        buf.writeCollection(this.pages, (buf2, page) -> buf2.writeString((String) page, 8192));
        buf.writeOptional(this.title, (buf2, title) -> buf2.writeString((String) title, 128));
    }

    @Override
    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
        serverPlayPacketListener.onBookUpdate(this);
    }

    public List<String> getPages() {
        return this.pages;
    }

    public Optional<String> getTitle() {
        return this.title;
    }

    public int getSlot() {
        return this.slot;
    }
}
