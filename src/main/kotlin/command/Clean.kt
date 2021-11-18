package com.github.samarium150.command

import com.github.samarium150.MiraiConsoleThrowIt
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.descriptor.ExperimentalCommandDescriptors
import net.mamoe.mirai.console.util.ConsoleExperimentalApi
import java.io.File
import java.util.Arrays

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