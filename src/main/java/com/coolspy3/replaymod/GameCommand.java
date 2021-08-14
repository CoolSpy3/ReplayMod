package com.coolspy3.replaymod;

import java.io.IOException;
import java.util.UUID;

import com.coolspy3.hypixelapi.APIConfig;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.StatusReply;
import net.hypixel.api.util.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class GameCommand {
    
    @SubscribeEvent
    public void register(ClientChatEvent event) {
        if(shouldTrigger(event.getMessage())) {
            event.setCanceled(true);
            Minecraft.getInstance().gui.getChat().addRecentChat(event.getMessage());
            try {
                String apiKey = APIConfig.getInstance().getAPIKey();
                if (apiKey == null) {
                    ReplayMod.sendMessage(TextFormatting.RED
                            + "Hypixel API is not linked!");
                    ReplayMod.sendMessage(TextFormatting.RED
                        + "Ensure that the Hypixel API mod is installed and run \"/linkhypixelapi\"");
                } else {
                    ReplayMod.executeAsync(() -> {
                        HypixelAPI api = new HypixelAPI(UUID.fromString(apiKey));
                        StatusReply reply = api.getStatus(Minecraft.getInstance().player.getUUID()).join();
                        StatusReply.Session session = reply.getSession();
                        if(session.getGameType() != GameType.SKYBLOCK) {
                            if(session.getMap() == null || session.getMode() == null || session.getMode().toLowerCase().equals("lobby")) {
                                ReplayMod.sendMessage(TextFormatting.RED + "You are not in a game!");
                                return;
                            }
                        }
                        invoke(session, event.getMessage());
                    });
                }
            } catch(IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public abstract boolean shouldTrigger(String msg);

    public abstract void invoke(StatusReply.Session session, String msg);

}
