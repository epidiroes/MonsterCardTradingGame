package org.example.apps.mctg.logic;

import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.enums.BattleStatus;
import org.example.apps.mctg.repository.CardRepository;
import org.example.apps.mctg.repository.DeckRepository;
import org.example.apps.mctg.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleLogicTest {

    @Test
    public void whenFightWithSameCard_shouldReturnDraw() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card = new Card("1", "1", "Knight", 10);

        // Act
        BattleStatus status1 = battleLogic.fight(card, card);
        BattleStatus status2 = battleLogic.fight(card, card);

        // Assert
        assertEquals(BattleStatus.DRAW, status1);
        assertEquals(BattleStatus.DRAW, status2);
    }

    @Test
    public void whenFightWithBetterCard1_shouldReturnCard1() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Knight", 10);
        Card card2 = new Card("1", "1", "Knight", 5);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);

        // Assert
        assertEquals(BattleStatus.CARD1, status1);
    }

    @Test
    public void whenFightWithBetterCard2_shouldReturnCard2() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Knight", 5);
        Card card2 = new Card("1", "1", "Knight", 10);

        // Act
        BattleStatus status = battleLogic.fight(card1, card2);

        // Assert
        assertEquals(BattleStatus.CARD2, status);
    }

    @Test
    public void whenFightWithKraken_krakenAlwaysWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Kraken", 1);
        Card card2 = new Card("1", "1", "Knight", 100);
        Card card3 = new Card("1", "1", "Dragon", 100);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);
        BattleStatus status3 = battleLogic.fight(card1, card3);
        BattleStatus status4 = battleLogic.fight(card3, card1);

        // Assert
        assertEquals(BattleStatus.CARD1, status1);
        assertEquals(BattleStatus.CARD2, status2);
        assertEquals(BattleStatus.CARD1, status3);
        assertEquals(BattleStatus.CARD2, status4);
    }

    @Test
    public void whenFightBetweenDragonAndGoblin_dragonAlwaysWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Dragon", 1);
        Card card2 = new Card("1", "1", "Goblin", 100);
        Card card3 = new Card("1", "1", "FireGoblin", 100);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);
        BattleStatus status3 = battleLogic.fight(card1, card3);
        BattleStatus status4 = battleLogic.fight(card3, card1);

        // Assert
        assertEquals(BattleStatus.CARD1, status1);
        assertEquals(BattleStatus.CARD2, status2);
        assertEquals(BattleStatus.CARD1, status3);
        assertEquals(BattleStatus.CARD2, status4);
    }

    @Test
    public void whenFightBetweenWizardAndOrk_wizardAlwaysWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Wizard", 1);
        Card card2 = new Card("1", "1", "Ork", 100);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);

        // Assert
        assertEquals(BattleStatus.CARD1, status1);
        assertEquals(BattleStatus.CARD2, status2);
    }

    @Test
    public void whenFightBetweenDragonAndFireElf_fireElfWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Dragon", 10);
        Card card2 = new Card("1", "1", "WaterElf", 10);
        Card card3 = new Card("1", "1", "FireElf", 10);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card1, card3);
        BattleStatus status3 = battleLogic.fight(card3, card1);
        BattleStatus status4 = battleLogic.fight(card2, card1);

        // Assert
        assertEquals(BattleStatus.DRAW, status1);
        assertEquals(BattleStatus.CARD2, status2);
        assertEquals(BattleStatus.CARD1, status3);
        assertEquals(BattleStatus.DRAW, status4);
    }

    @Test
    public void whenFightBetweenKnightAndWaterSpell_WaterSpellWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "Knight", 10);
        Card card2 = new Card("1", "1", "FireSpell", 1);
        Card card3 = new Card("1", "1", "WaterSpell", 1);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card1, card3);
        BattleStatus status3 = battleLogic.fight(card3, card1);
        BattleStatus status4 = battleLogic.fight(card2, card1);

        // Assert
        assertEquals(BattleStatus.CARD1, status1);
        assertEquals(BattleStatus.CARD2, status2);
        assertEquals(BattleStatus.CARD1, status3);
        assertEquals(BattleStatus.CARD2, status4);
    }

    @Test
    public void whenFightBetweenFireAndWater_WaterWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "FireSpell", 10);
        Card card2 = new Card("1", "1", "WaterSpell", 10);
        Card card3 = new Card("1", "1", "WaterGoblin", 10);
        Card card4 = new Card("1", "1", "FireGoblin", 10);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);
        BattleStatus status3 = battleLogic.fight(card1, card3);
        BattleStatus status4 = battleLogic.fight(card3, card1);
        BattleStatus status5 = battleLogic.fight(card2, card4);
        BattleStatus status6 = battleLogic.fight(card4, card2);


        // Assert
        assertEquals(BattleStatus.CARD2, status1);
        assertEquals(BattleStatus.CARD1, status2);
        assertEquals(BattleStatus.CARD2, status3);
        assertEquals(BattleStatus.CARD1, status4);
        assertEquals(BattleStatus.CARD1, status5);
        assertEquals(BattleStatus.CARD2, status6);
    }

    @Test
    public void whenFightBetweenWaterAndRegular_RegularWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "WaterSpell", 10);
        Card card2 = new Card("1", "1", "RegularSpell", 10);
        Card card3 = new Card("1", "1", "RegularGoblin", 10);
        Card card4 = new Card("1", "1", "WaterGoblin", 10);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);
        BattleStatus status3 = battleLogic.fight(card1, card3);
        BattleStatus status4 = battleLogic.fight(card3, card1);
        BattleStatus status5 = battleLogic.fight(card2, card4);
        BattleStatus status6 = battleLogic.fight(card4, card2);


        // Assert
        assertEquals(BattleStatus.CARD2, status1);
        assertEquals(BattleStatus.CARD1, status2);
        assertEquals(BattleStatus.CARD2, status3);
        assertEquals(BattleStatus.CARD1, status4);
        assertEquals(BattleStatus.CARD1, status5);
        assertEquals(BattleStatus.CARD2, status6);
    }

    @Test
    public void whenFightBetweenRegularAndFire_FireWins() {
        // Arrange
        BattleLogic battleLogic = new BattleLogic(new DeckRepository(), new CardRepository(), new UserRepository());
        Card card1 = new Card("1", "1", "RegularSpell", 10);
        Card card2 = new Card("1", "1", "FireSpell", 10);
        Card card3 = new Card("1", "1", "FireGoblin", 10);
        Card card4 = new Card("1", "1", "RegularGoblin", 10);

        // Act
        BattleStatus status1 = battleLogic.fight(card1, card2);
        BattleStatus status2 = battleLogic.fight(card2, card1);
        BattleStatus status3 = battleLogic.fight(card1, card3);
        BattleStatus status4 = battleLogic.fight(card3, card1);
        BattleStatus status5 = battleLogic.fight(card2, card4);
        BattleStatus status6 = battleLogic.fight(card4, card2);


        // Assert
        assertEquals(BattleStatus.CARD2, status1);
        assertEquals(BattleStatus.CARD1, status2);
        assertEquals(BattleStatus.CARD2, status3);
        assertEquals(BattleStatus.CARD1, status4);
        assertEquals(BattleStatus.CARD1, status5);
        assertEquals(BattleStatus.CARD2, status6);
    }
}