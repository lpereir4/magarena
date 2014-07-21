[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Draw),
        "Search"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source),
                new MagicPayManaCostEvent(source, "{2}{U}"),
                new MagicDiscardEvent(source),
                new MagicSacrificePermanentEvent(source, MagicTargetChoice.SACRIFICE_LAND)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "PN searches his or her library for up to two basic land cards and puts them onto the battlefield. "+
                "Then PN shuffles his or her library."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.addEvent(new MagicSearchOntoBattlefieldEvent(
                event,
                new MagicFromCardFilterChoice(
                    MagicTargetFilterFactory.BASIC_LAND_CARD_FROM_LIBRARY,
                    2, 
                    true, 
                    "to put onto the battlefield"
                )
            ));
        }
    }
]
