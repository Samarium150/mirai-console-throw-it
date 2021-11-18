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
package com.github.samarium150.command

import com.github.samarium150.MiraiConsoleThrowIt
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.io.File
import java.util.*

object Clean: SimpleCommand(
    MiraiConsoleThrowIt,
    primaryName = "th-clean",
    secondaryNames = arrayOf("清理"),
    description = "清理丢人图缓存"
) {
    @ConsoleExperimentalApi
    @ExperimentalCommandDescriptors
    override val prefixOptional: Boolean = true

    @Suppress("unused")
    @Handler
    suspend fun CommandSender.handle() {
        val dir = File(MiraiConsoleThrowIt.dataPath)
        if (dir.exists() && dir.isDirectory) {
            try {
                Arrays.stream(dir.listFiles()).forEachOrdered {
                    if (it.isFile) {
                        it.delete()
                    }
                }
                sendMessage("清理成功")
            } catch (e: Exception) {
                MiraiConsoleThrowIt.logger.error(e)
                sendMessage("清理失败了，查看日志获取更多信息")
            }
        }
    }
}