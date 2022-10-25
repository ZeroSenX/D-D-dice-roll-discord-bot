package zeroSen;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Main {
    public static void main(String[] args) {
        Dotenv config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Am i a robot?"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        ShardManager shardManager = builder.build();

        // Register a new event listener
        shardManager.addEventListener(new EventListener());
    }
}