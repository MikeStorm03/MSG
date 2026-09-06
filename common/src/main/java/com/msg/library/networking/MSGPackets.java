package com.msg.library.networking;

import java.util.HashSet;
import java.util.Set;

import com.msg.library.Constants;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStackTemplate;

public class MSGPackets {

	// // This is sent by the client so the server is know that the client has mod
	// public record HasMod() implements CustomPacketPayload {
	// 	public static final CustomPacketPayload.Type<HasMod> ID = new CustomPacketPayload.Type<>(Constants.resourceLocation("has_mod"));
	// 	public static final StreamCodec<RegistryFriendlyByteBuf, HasMod> CODEC = StreamCodec.unit(new HasMod());

	// 	@Override
	// 	public Type<? extends CustomPacketPayload> type() {
	// 		return ID;
	// 	}
	// }

	// This send brewing ingredient to the client so the animation sync better.
	public record BrewingPackets(Set<ItemStackTemplate> brewingIngredients, Set<ItemStackTemplate> brewingInputs) implements CustomPacketPayload {
		public static final CustomPacketPayload.Type<BrewingPackets> ID = new CustomPacketPayload.Type<>(Constants.resourceLocation("brewing_ingredients"));
		public static final StreamCodec<RegistryFriendlyByteBuf, BrewingPackets> CODEC = StreamCodec.composite(
			ByteBufCodecs.collection(HashSet::new, ItemStackTemplate.STREAM_CODEC),
			BrewingPackets::brewingIngredients,
			ByteBufCodecs.collection(HashSet::new, ItemStackTemplate.STREAM_CODEC),
			BrewingPackets::brewingInputs,
			BrewingPackets::new
		);

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return ID;
		}
	}
}
