package com.coolspy3.replaymod;

import net.hypixel.api.reply.StatusReply.Session;
import net.minecraft.util.text.TextFormatting;

public class WhatGameIsThisCommand extends GameCommand {

    @Override
    public boolean shouldTrigger(String msg) {
        return msg.matches("/whatgameisthis( .*)?") || msg.matches("/wgit( .*)?");
    }

    @Override
    public void invoke(Session session, String msg) {
        ReplayMod.sendMessage(TextFormatting.AQUA + "You are playing \"" + ReplayMod.gameCode(session) + "\"");
    }

}
