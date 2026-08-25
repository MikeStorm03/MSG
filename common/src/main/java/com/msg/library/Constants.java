/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;

public interface Constants {

	String MOD_ID = "msg_library";
	String MOD_NAME = "MSG";
	Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	static ResourceLocation resourceLocation(String name) {
		return ResourceLocation.fromNamespaceAndPath("msg", name);
	}
}