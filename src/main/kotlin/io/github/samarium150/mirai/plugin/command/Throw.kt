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
package io.github.samarium150.mirai.plugin.command

import io.github.samarium150.mirai.plugin.MiraiConsoleThrowIt
import io.github.samarium150.mirai.plugin.Utils
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.contact.User
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import javax.imageio.ImageIO

object Throw: SimpleCommand(
    MiraiConsoleThrowIt,
    primaryName = "throw",
    secondaryNames = arrayOf("丢"),
    description = "把@的对象丢出去"
) {
    @ConsoleExperimentalApi
    @ExperimentalCommandDescriptors
    override val prefixOptional: Boolean = true

    val template: BufferedImage

    init {
        val dir = File(MiraiConsoleThrowIt.dataPath)
        if (!dir.exists() || !dir.isDirectory) dir.mkdirs()
        val templateFile: URL? = javaClass.classLoader.getResource("template.png")
        if (templateFile != null)
            template = ImageIO.read(templateFile)
        else throw FileNotFoundException("没有找到template.png")
    }

    @Suppress("unused")
    @Handler
    suspend fun CommandSenderOnMessage<*>.handle(target: User) {
        val resultPath = MiraiConsoleThrowIt.dataPath + target.id + ".png"
        val result = File(resultPath)
        if (!result.exists()) {
            Utils.processAvatar(target.avatarUrl, resultPath)
                .onFailure { exception ->
                    run {
                        MiraiConsoleThrowIt.logger.error(exception)
                        sendMessage("生成图片失败了，查看日志获取更多信息")
                    }
                }
        }
        if (result.exists()) {
            MiraiConsoleThrowIt.logger.info("丢出"+target.nick)
            sendMessage(target.uploadImage(result))
        }
    }
}
