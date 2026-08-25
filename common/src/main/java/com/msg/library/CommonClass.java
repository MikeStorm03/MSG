/*
MSG
Copyright (C) 2026 - MikeStorm03

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
*/
package com.msg.library;

import com.msg.library.platform.Services;

public class CommonClass {

    public static void init() {

        Constants.LOG.info("{} is runnning on {}!", Constants.MOD_NAME, Services.PLATFORM.getPlatformName());
    }
}