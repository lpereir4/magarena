package magic.card;

import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPayedCost;
import magic.model.action.MagicCardOnStackAction;
import magic.model.action.MagicCounterItemOnStackAction;
import magic.model.action.MagicDrawAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.event.MagicSpellCardEvent;
import magic.model.stack.MagicCardOnStack;

public class Remand {
    public static final MagicSpellCardEvent S = new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    cardOnStack,
                    MagicTargetChoice.NEG_TARGET_SPELL,
                    this,
                    "Counter target spell$. If you do, return that spell card to its owner's hand. Draw a card.");
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] data,
                final Object[] choiceResults) {
            event.processTargetCardOnStack(game,choiceResults,0,new MagicCardOnStackAction() {
                public void doAction(final MagicCardOnStack targetSpell) {
                    game.doAction(new MagicCounterItemOnStackAction(targetSpell,MagicLocationType.OwnersHand));
                    game.doAction(new MagicDrawAction(event.getPlayer(),1));
                }
            });
        }
    };
}
