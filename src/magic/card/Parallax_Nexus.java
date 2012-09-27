package magic.card;

import magic.model.MagicCard;
import magic.model.MagicCardList;
import magic.model.MagicCounterType;
import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicSource;
import magic.model.action.MagicCardAction;
import magic.model.action.MagicExileUntilThisLeavesPlayAction;
import magic.model.action.MagicReturnExiledUntilThisLeavesPlayAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicRemoveCounterEvent;
import magic.model.event.MagicTiming;
import magic.model.target.MagicGraveyardTargetPicker;
import magic.model.trigger.MagicWhenLeavesPlayTrigger;

public class Parallax_Nexus {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
        new MagicCondition[]{
            MagicCondition.SORCERY_CONDITION,
            MagicCondition.CHARGE_COUNTER_CONDITION
        },
        new MagicActivationHints(MagicTiming.Main),
        "Exile"
    ) {

        @Override
        public MagicEvent[] getCostEvent(final MagicSource source) {
            return new MagicEvent[]{
                new MagicRemoveCounterEvent((MagicPermanent)source,MagicCounterType.Charge,1)
            };
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                source.getController().getOpponent(),
                MagicTargetChoice.TARGET_CARD_FROM_HAND,
                new MagicGraveyardTargetPicker(false),
                this,
                "PN exiles a card from his or her hand."
            );
        }

        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event,final Object[] data,final Object[] choiceResults) {
            event.processTargetCard(game,choiceResults,0,new MagicCardAction() {
                public void doAction(final MagicCard card) {
                    game.doAction(new MagicExileUntilThisLeavesPlayAction(
                        event.getPermanent(),
                        card,
                        MagicLocationType.OwnersHand
                    ));
                }
            });
        }
    };
    
    public static final MagicWhenLeavesPlayTrigger T3 = new MagicWhenLeavesPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPermanent data) {
            if (permanent == data && !permanent.getExiledCards().isEmpty()) {
                final MagicCardList clist = new MagicCardList(permanent.getExiledCards());
                return new MagicEvent(
                    permanent,
                    this,
                    clist.size() > 1 ?
                        "Return exiled cards to their owner's hand" :
                        "Return " + clist.get(0) + " to its owner's hand"
                );
            }
            return MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object data[],
                final Object[] choiceResults) {
            final MagicPermanent permanent = event.getPermanent();
            game.doAction(new MagicReturnExiledUntilThisLeavesPlayAction(permanent,MagicLocationType.OwnersHand));
        }
    };
}
