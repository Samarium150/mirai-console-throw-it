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
package io.github.samarium150.mirai.plugin

import io.github.samarium150.mirai.plugin.command.Clean
import io.github.samarium150.mirai.plugin.command.Throw
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.plugin.id
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object MiraiConsoleThrowIt: KotlinPlugin(
    JvmPluginDescription(
        id = "io.github.samarium150.mirai.plugin.mirai-console-throw-it",
        name = "Throw It",
        version = "1.2.1"
    ) {
        author("Samarium150")
        info("丢'人'插件, 用指令把@的人丢出去")
    }
) {

    val dataPath = System.getProperty("user.dir") + "/data/${id}/"

    init {
        System.setProperty("java.awt.headless", "true")
    }

    override fun onEnable() {
        Throw.register()
        Clean.register()
        logger.info("Plugin loaded")
    }

    override fun onDisable() {
        Throw.unregister()
        Clean.unregister()
        logger.info("Plugin unloaded")
    }
}
