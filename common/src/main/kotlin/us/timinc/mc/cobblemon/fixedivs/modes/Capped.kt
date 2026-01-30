package us.timinc.mc.cobblemon.fixedivs.modes

import com.cobblemon.mod.common.api.events.starter.StarterChosenEvent
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.IVs
import us.timinc.mc.cobblemon.fixedivs.FixedIvs
import us.timinc.mc.cobblemon.timcore.AbstractHandler
import us.timinc.mc.cobblemon.timcore.getIdentifier

object Capped : AbstractHandler<StarterChosenEvent>() {
    override fun handle(evt: StarterChosenEvent) {
        if (!(FixedIvs.enabledCount == 1 && FixedIvs.config.capped.enabled)) return

        val debugger = FixedIvs.debugger.getCaseDebugger()

        debugger.debug("${evt.player.name.string} chose starter ${evt.pokemon.getIdentifier()}.")
        debugger.debug("Capped mode is enabled.")

        if (!FixedIvs.config.capped.leaveRolledPerfects) {
            debugger.debug("Re-rolling any rolled perfects...")
            Stats.PERMANENT.forEach { stat ->
                if (evt.pokemon.ivs[stat] == IVs.MAX_VALUE) {
                    val nonPerfectValue = (0..<IVs.MAX_VALUE).random()
                    evt.pokemon.ivs[stat] = nonPerfectValue
                    debugger.debug("Re-rolled $stat from perfect to $nonPerfectValue.")
                }
            }
        }

        val toPerfect = Stats.PERMANENT.shuffled().take(FixedIvs.config.capped.count)
        debugger.debug("Rolled $toPerfect to perfect.")
        toPerfect.forEach { stat ->
            evt.pokemon.ivs[stat] = IVs.MAX_VALUE
            debugger.debug("Set $stat to max (${IVs.MAX_VALUE}).")
        }
    }
}