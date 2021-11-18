/**
 * Copyright (c) 2020-2021 Samarium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 */
package com.github.samarium150

import com.github.samarium150.command.Clean
import com.github.samarium150.command.Throw
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object MiraiConsoleThrowIt: KotlinPlugin(
    JvmPluginDescription(
        id = "com.github.samarium150.mirai-console-throw-it",
        name = "mirai-console-throw-it",
        version = "1.0.0"
    ) {
        author("Samarium150")
    },
) {

    val dataPath = System.getProperty("user.dir") + "/data/mirai-console-throw-it/"

    init {
        System.setProperty("java.awt.headless", "true")
    }

    override fun onEnable() {
        Throw.register()
        Clean.register()
        logger.info("mirai-console-throw-it loaded")
    }

    override fun onDisable() {
        Throw.unregister()
        Clean.unregister()
        logger.info("mirai-console-throw-it unloaded")
    }
}