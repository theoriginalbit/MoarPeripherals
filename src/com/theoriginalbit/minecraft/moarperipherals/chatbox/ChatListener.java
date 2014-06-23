package com.theoriginalbit.minecraft.moarperipherals.chatbox;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.collect.Lists;

public class ChatListener {
	public static final ChatListener instance = new ChatListener();
	public ArrayList<TileChatBox> chatboxes = Lists.newArrayList();
	
	@ForgeSubscribe
	public void onServerChatEvent(ServerChatEvent event) {
		for (TileChatBox tile : chatboxes) {
			tile.onChatEvent(event.player, event.message);
		}
	}
	
	@ForgeSubscribe
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			DamageSource source = event.source;
			String killer = null;
			if (source instanceof EntityDamageSource) {
				Entity ent = ((EntityDamageSource) source).getEntity();
				if (ent != null)
					killer = ent.getEntityName();
			}
			String damage = source.getDamageType();
			
			for (TileChatBox tile : chatboxes) {
				tile.onDeathEvent(event.entity, killer, damage);
			}
		}
	}
}
