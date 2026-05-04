package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        List<Hero> party = new ArrayList<>();
        party.add(new Hero("Arthur", 120, 30, 15));
        party.add(new Hero("Merlin", 70, 150, 10, 5, 100));

        QuestLog log = new QuestLog();
        log.add(new Quest("Clear the Goblin Cave", QuestPriority.LOW, 50, false));
        log.add(new Quest("Slay the Ancient Dragon", QuestPriority.URGENT, 5000, true));
        log.add(new Quest("Escort the Merchant", QuestPriority.NORMAL, 200, false));
        log.add(new Quest("Investigate Haunted Ruins", QuestPriority.HIGH, 600, false));
        log.add(new Quest("Defeat the Bandit King", QuestPriority.NORMAL, 300, false));

        GuildHall hall = new GuildHall();
        Quartermaster qm = new Quartermaster("Bofur", hall);
        Scout scout = new Scout("Legolas", hall);
        Healer healer = new Healer("Elrond", hall);
        Captain captain = new Captain("Aragorn", hall);
        Loremaster loremaster = new Loremaster("Gandalf", hall);

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, log, hall);
        System.out.println(result);

        System.out.println("\n--- Testing Open/Closed Proof (Part 4) ---");

        scout.reportRoute("history", "Found an ancient temple in the Goblin Cave.");
        captain.issueOrder("lore", "We need details on the Ancient Dragon's weakness.");

        System.out.println("\n--- Traversing Quests: Reward Sorted ---");
        QuestIterator rewardIterator = log.rewardSorted();
        while (rewardIterator.hasNext()) {
            Quest q = rewardIterator.next();
            System.out.println(q.getTitle() + " - " + q.getRewardGold() + "G");
        }
    }
}