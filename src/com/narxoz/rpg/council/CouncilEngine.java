package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.GuildMember;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;

import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;
        int membersNotified = 0;

        GuildMember engineSender = new GuildMember("Council System", hall) {
            @Override
            public void receive(String topic, GuildMember from, String payload) {}
        };

        System.out.println("--- Traversing Quests: Ordered ---");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            hall.dispatch("orders", engineSender, "Reviewing quest: " + q.getTitle());
            messagesRouted++;
            membersNotified++;
        }

        System.out.println("--- Traversing Quests: High Priority ---");
        QuestIterator priority = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (priority.hasNext()) {
            Quest q = priority.next();
            questsTraversed++;
            hall.dispatch("supplies", engineSender, "Prepare gear for high priority: " + q.getTitle());
            messagesRouted++;
            membersNotified++;

            if (q.isUrgent()) {
                hall.dispatch("wounds", engineSender, "Medical team on standby for urgent quest: " + q.getTitle());
                messagesRouted++;
                membersNotified++;
            }
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}