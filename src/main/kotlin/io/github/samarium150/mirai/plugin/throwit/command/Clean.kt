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
package io.github.samarium150.mirai.plugin.throwit.command

import io.github.samarium150.mirai.plugin.throwit.MiraiConsoleThrowIt
import io.github.samarium150.mirai.plugin.throwit.util.cleanupDirectory
import io.github.samarium150.mirai.plugin.throwit.util.getMidnight
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

    private val cacheDir = File(MiraiConsoleThrowIt.dataPath)

    private val scheduler = Timer()

    private val task = object : TimerTask() {
        override fun run() {
            cleanupDirectory(cacheDir)
                .onFailure {
                    MiraiConsoleThrowIt.logger.error("定时清理缓存失败", it)
                }
                .onSuccess {
                    MiraiConsoleThrowIt.logger.info("定时清理缓存成功")
                }
        }
    }

    init {
        scheduler.scheduleAtFixedRate(task, getMidnight(), 24 * 60 * 60 * 1000)
    }

    @Suppress("unused")
    fun finalize() {
        task.cancel()
        scheduler.cancel()
    }

    @Suppress("unused")
    @Handler
    suspend fun CommandSender.handle() {
        cleanupDirectory(cacheDir)
            .onFailure { exception ->
                run {
                    MiraiConsoleThrowIt.logger.error(exception)
                    sendMessage("清理失败了，查看日志获取更多信息")
                }
            }
            .onSuccess {
                sendMessage("清理成功")
            }
    }
}
