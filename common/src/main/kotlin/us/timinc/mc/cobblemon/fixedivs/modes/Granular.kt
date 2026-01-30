package us.timinc.mc.cobblemon.fixedivs.modes

import com.cobblemon.mod.common.api.events.starter.StarterChosenEvent
import us.timinc.mc.cobblemon.fixedivs.FixedIvs
import us.timinc.mc.cobblemon.timcore.AbstractHandler
import us.timinc.mc.cobblemon.timcore.getIdentifier

object Granular : AbstractHandler<StarterChosenEvent>() {
    override fun handle(evt: StarterChosenEvent) {
        if (!(FixedIvs.enabledCount == 1 && FixedIvs.config.granular.enabled)) return

        val debugger = FixedIvs.debugger.getCaseDebugger()

        debugger.debug("${evt.player.name.string} chose starter ${evt.pokemon.getIdentifier()}.")
        debugger.debug("Granular mode is enabled.")

        FixedIvs.config.granular.values.forEach { (stat, i) ->
            evt.pokemon.ivs[stat] = i
            debugger.debug("Set $stat to $i")
        }
    }
}