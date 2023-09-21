package me.pi3ro.pvplounge.scoreboard

import me.pi3ro.pvplounge.PvPLounge
import me.pi3ro.pvplounge.utils.Utils
import io.github.thatkawaiisam.assemble.AssembleAdapter
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

/**
 * Created by: Pi3ro
 * Project: PvPLoungeHub
 * Date: 2023-09-14 @ 05:15
 **/
class ScoreboardProvider : AssembleAdapter {
    private val config = PvPLounge.getInstance().config

    override fun getTitle(p0: Player?): String {
        return Utils.translate(config.getString("SCOREBOARD.TITLE"))
    }

    override fun getLines(p0: Player?): MutableList<String> {
        val lines = mutableListOf<String>()
        val rawLines = config.getStringList("SCOREBOARD.LINES")

        if (p0 != null) {
            for (line in rawLines) {
                val processedLine = PlaceholderAPI.setPlaceholders(p0, line)
                lines.add(Utils.translate(processedLine))
            }
        }

        return lines
    }
}