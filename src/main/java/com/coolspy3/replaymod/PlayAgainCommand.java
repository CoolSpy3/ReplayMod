package com.coolspy3.replaymod;

import net.hypixel.api.reply.StatusReply.Session;
import net.minecraft.client.Minecraft;

public class PlayAgainCommand extends GameCommand {

    @Override
    public boolean shouldTrigger(String msg) {
        return msg.matches("/pa( .*)?");
    }

    @Override
    public void invoke(Session session, String msg) {
        Minecraft.getInstance().player.chat("/play " + ReplayMod.gameCode(session));
    }

}
