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

import com.github.samarium150.command.Throw
import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.geometry.Positions
import java.awt.AlphaComposite
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.util.*
import javax.imageio.ImageIO

object Utils {
    fun processAvatar(url: String, resultPath: String) = runCatching {
        val avatarUrl = URL(url)
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
        graphics = Throw.template.createGraphics()
        graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP)
        graphics.drawImage(processedAvatar, 19, 181, 137, 137, null)
        graphics.dispose()

        Thumbnails.of(Throw.template)
            .size(512, 512)
            .toFile(resultPath)
    }

    fun cleanupDirectory(directory: File) = runCatching {
        if (directory.exists() && directory.isDirectory) {
            Arrays.stream(directory.listFiles()).forEachOrdered {
                if (it.isFile) {
                    it.delete()
                }
            }
        }
    }
}