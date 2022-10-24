package org.example;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class Roller {
    private MessageReceivedEvent event;
    private final String message;
    private int dices;
    private int diceType;
    private final ArrayList<Integer> results;
    private int result;
    private int modifier;
    private boolean kh;
    private boolean kl;
    private boolean error;
    private StringBuilder messageToSend;

    public Roller(String message){
        this.message = message;
        results = new ArrayList<>();
        result = 0;
        messageToSend = new StringBuilder();
        analyzer();
    }

    public Roller(MessageReceivedEvent e){
        this.event = e;
        this.message = e.getMessage().getContentRaw();
        results = new ArrayList<>();
        result = 0;
        messageToSend = new StringBuilder();
        analyzer();
    }


    protected void analyzer(){
        // !r2d20kh1+4
        if(message.contains("r")) dices = char2int(message.indexOf("r") + 1);
        else error = true;
        if(message.contains("d")) diceType = char2int(message.indexOf("d") + 1);
        else error = true;

        if(message.contains("kh") || message.contains("vantaggio")) kh = true;
        else if(message.contains("kl") || message.contains("svantaggio")) kl = true;

        if(message.contains("+"))
            modifier = char2int(message.indexOf("+")+1);
        else if (message.contains("-"))
            modifier = char2int(message.indexOf("-")+1) * -1;

    }


    protected int char2int(int i){
        int start = 0, end = 0;

        while(i < message.length() && Character.isDigit(message.charAt(i)) && !error){
            if(Character.isDigit(message.charAt(i))){
                if(start == 0) start = i;
                end = i;
            }
            i++;
        }
        int num = 0;
        try {
            num = Integer.parseInt(message.substring(start, end+1));
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            error = true;
        }
        return num;
    }

    public void rollDices(){
        if(!error) {
            for (int i = 0; i < dices; i++) {
                results.add((int) (Math.random() * diceType) + 1);
            }
            if (kh)
                result = Collections.max(results);
            else if (kl)
                result = Collections.min(results);
            else {
                result = results.stream().mapToInt(Integer::intValue).sum();
            }
            result += modifier;
        }
    }
    public String buildMessageToSend(){
        if(!error){
            messageToSend.append(dices).append("d").append(diceType).append("\n");
            if(event != null) messageToSend.append(" by ").append(event.getAuthor().getName()).append("\n");
            messageToSend.append("Result: ").append(result).append("\n");
            messageToSend.append("Modifier: ");
            if(modifier > 0) messageToSend.append("+");
            messageToSend.append(modifier).append("\n");
            if(kh)
                messageToSend.append("Advantage \u2705").append("\n");
            else if (kl)
                messageToSend.append("disadvantage \u2705").append("\n");

            messageToSend.append("Dice rolled:\n");
            for(Integer i : results)
                messageToSend.append(i).append(" ");

            messageToSend.append("\n");

        }
        else messageToSend = new StringBuilder("Errore");

        return messageToSend.toString();
    }

    public String toString(){
        return (dices + " d " + diceType + " kh " + kh + " kl " + kl + " modificatore " + modifier + " errore " + error);
    }

    public static void main(String [] args){
        System.out.println("Only for test");
        Roller a = new Roller("!r2d8+4");
        a.rollDices();
        System.out.println(a.buildMessageToSend());
    }
}