package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPlayer;
import magic.model.MagicPowerToughness;
import magic.model.mstatic.MagicCDA;
import magic.model.target.MagicTargetFilter;

public class Nightmare {
    public static final MagicCDA CDA = new MagicCDA() {
        @Override
        public void modPowerToughness(final MagicGame game,final MagicPlayer player,final MagicPowerToughness pt) {
            final int size = game.filterTargets(player,MagicTargetFilter.TARGET_SWAMP_YOU_CONTROL).size();
            pt.set(size, size);
        }
    };
}
