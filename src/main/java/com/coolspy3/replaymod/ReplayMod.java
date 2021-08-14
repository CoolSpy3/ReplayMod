package com.coolspy3.replaymod;

import com.mojang.brigadier.LiteralMessage;

import net.hypixel.api.reply.StatusReply;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("replaymod")
public class ReplayMod {
    
    public ReplayMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayAgainCommand());
        MinecraftForge.EVENT_BUS.register(new SPlayCommand());
        MinecraftForge.EVENT_BUS.register(new WhatGameIsThisCommand());
    }

    public static void executeAsync(Runnable function) {
        Thread thread = new Thread(function);
        thread.setDaemon(true);
        thread.start();
    }

    public static void sendMessage(String msg) {
        Minecraft.getInstance().player.sendMessage(TextComponentUtils.fromMessage(new LiteralMessage(msg)), Util.NIL_UUID);
    }

    public static String filterSkywars(String gameCode) {
        return gameCode.startsWith("skywars_") ? gameCode.substring("skywars_".length()) : gameCode;
    }

    public static String filterSkyblock(String gameCode) {
        return gameCode.startsWith("skyblock_") ? "skyblock" : gameCode;
    }

    public static String gameCode(StatusReply.Session session) {
        return filterSkywars(filterSkyblock(session.getGameType().getDbName().toLowerCase() + "_" + session.getMode().toLowerCase()));
    }

}
