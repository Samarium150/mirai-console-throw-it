package com.github.samarium150.command

import com.github.samarium150.MiraiConsoleThrowIt
import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.geometry.Positions
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.contact.User
import java.awt.AlphaComposite
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
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

    private val template: BufferedImage

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
    suspend fun CommandSender.handle(target: User) {
        val resultPath = MiraiConsoleThrowIt.dataPath + target.id + ".png"
        val result = File(resultPath)
        if (!result.exists()) {
            try {
                val avatarUrl = URL(target.avatarUrl)
                val avatar = ImageIO.read(avatarUrl)
                var processedAvatar = BufferedImage(avatar.width, avatar.height, BufferedImage.TYPE_INT_ARGB)
                var graphics = processedAvatar.createGraphics()
                graphics.setRenderingHints(
                    RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                )
                graphics.clip = Ellipse2D.Double(0.0, 0.0, avatar.width.toDouble(), avatar.height.toDouble())
                graphics.drawImage(avatar, 0, 0, null)
                graphics.dispose()
                processedAvatar = Thumbnails.of(processedAvatar)
                    .size(136, 136)
                    .rotate(-160.0)
                    .asBufferedImage()
                processedAvatar = Thumbnails.of(processedAvatar)
                    .sourceRegion(Positions.CENTER, 136, 136)
                    .size(136, 136)
                    .keepAspectRatio(false)
                    .asBufferedImage()
                graphics = template.createGraphics()
                graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP)
                graphics.drawImage(processedAvatar, 19, 181, 137, 137, null)
                graphics.dispose()
                Thumbnails.of(template)
                    .size(512, 512)
                    .toFile(resultPath)
            } catch (e: Exception) {
                MiraiConsoleThrowIt.logger.error(e)
                sendMessage("丢失败了，查看日志获取更多信息")
            }
        }
        if (result.exists()) {
            MiraiConsoleThrowIt.logger.info("丢出"+target.nick)
            sendMessage(target.uploadImage(result))
        }
    }
}