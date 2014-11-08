/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.reference;

public final class ModInfo {

    public static final String ID = "MoarPeripherals";
    public static final String NAME = ID;
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL = ID.toLowerCase();
    public static final String RESOURCE_DOMAIN = ID.toLowerCase();
    public static final String CONFIG_DOMAIN = RESOURCE_DOMAIN + ".config.";

    private static final String COM_PACKAGE = "com.theoriginalbit";
    private static final String MOD_PACKAGE = COM_PACKAGE + ".moarperipherals";

    public static final String GUI_FACTORY = MOD_PACKAGE + ".common.config.ConfigFactoryMoarP";
    public static final String PROXY_CLIENT = MOD_PACKAGE + ".client.ProxyClient";
    public static final String PROXY_SERVER = MOD_PACKAGE + ".common.ProxyCommon";

    public static final String DEPENDENCIES = "required-after:ComputerCraft@1.64,);";

}