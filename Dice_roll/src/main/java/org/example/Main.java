package org.example;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        String token = "MTAzMjY5MDI5MDM2NjA5NTM5MA.GBbEBi.CMY5ww564vnE6IU97tfELUFrlsXbRykqIoz3d4";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());


        Roller roll = new Roller(event);
        roll.rollDices();
        event.getChannel().sendMessage(roll.buildMessageToSend()).queue();

    }

}
