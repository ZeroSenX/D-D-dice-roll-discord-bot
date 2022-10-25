package zeroSen;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()){
            return;
        }
        String message = event.getMessage().getContentRaw();

        if(message.startsWith("!r")) {
            Roll roll = new Roll(event);
            roll.rollDices();
            event.getChannel().sendMessage(roll.buildMessageToSend()).queue();
        }
    }
}
