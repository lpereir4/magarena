[
    new MagicWhenComesIntoPlayTrigger() {
       @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPayedCost payedCost) {      
            return new MagicEvent(
                permanent,
                this,
                "PN may search his or her library for a Giant card, reveal it, then shuffle his or her library and put that card on top of it."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            game.addEvent(new MagicSearchOntoLibraryEvent(
                event,
                new MagicMayChoice(
                    "Search for a Giant card?",
                    new MagicTargetChoice("a Giant card from your library")
                )
            ));
        }
    }
]
