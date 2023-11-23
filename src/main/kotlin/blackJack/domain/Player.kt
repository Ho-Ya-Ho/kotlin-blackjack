package blackJack.domain

import blackJack.domain.Status.HIT
import blackJack.error.ErrorMessage

class Player(val name: String, val cards: Cards = Cards(mutableListOf()), var status: Status = HIT) {

    init {
        require(name.isNotEmpty()) { ErrorMessage.EMPTY_NAME.message }
    }

    fun isHit(): Boolean = status == HIT

    fun addCard(dealer: Dealer, answer: String) {
        if (answer == "y") {
            Status.validationAddCard(status)
            val card = dealer.cardDeck.drawCard()
            cards.addCard(card)
            status = Status.calculateStatus(cards.calculateTotalScore(), cards.cardSize)
        }
    }

    fun receiveInitialCards(initialCards: Cards) {
        cards.addCard(initialCards.drawCard())
        cards.addCard(initialCards.drawCard())
        status = Status.calculateStatus(cards.calculateTotalScore(), cards.cardSize)
    }

    companion object {
        fun splitNames(inputNames: String): List<String> {
            return inputNames.split(",").map { it.trim() }.toList()
        }

        fun createPlayer(name: String): Player {
            return Player(name)
        }
    }
}
