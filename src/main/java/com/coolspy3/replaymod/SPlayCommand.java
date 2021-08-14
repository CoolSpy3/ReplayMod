package com.coolspy3.replaymod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.hypixel.api.reply.StatusReply.Session;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;

public class SPlayCommand extends GameCommand {

    public static final String regex = "/splay ([a-zA-Z0-9_\\-\\/]+)( .*)?";
    public static final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean shouldTrigger(String msg) {
        return msg.matches("/splay( .*)?");
    }

    @Override
    public void invoke(Session session, String msg) {
        Matcher matcher = pattern.matcher(msg);
        if(matcher.matches()) {
            MinecraftForge.EVENT_BUS.post(new ClientChatEvent("/sc set " + matcher.group(1) + " /play " + ReplayMod.gameCode(session)));
        } else {
            ReplayMod.sendMessage(TextFormatting.RED + "Usage: /splay <command>");
        }
    }

}
