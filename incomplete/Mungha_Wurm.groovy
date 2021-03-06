[
    new MagicAtYourUpkeepTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game, final MagicPermanent permanent, final MagicPlayer upkeepPlayer) {
            return new MagicEvent(
                permanent,
                upkeepPlayer,
                MagicTargetChoice.LAND_YOU_CONTROL,
                MagicTapTargetPicker.Untap,
                this,
                "PN untaps a land PN controls\$."
            );
        }
        @Override
        public void executeEvent(final MagicGame game,final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicUntapAction(it));
            });
        }
    }
]
