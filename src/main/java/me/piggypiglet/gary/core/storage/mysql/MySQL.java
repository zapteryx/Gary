package me.piggypiglet.gary.core.storage.mysql;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.gary.GaryBot;
import me.piggypiglet.gary.core.objects.Constants;
import me.piggypiglet.gary.core.storage.json.GTypes;
import me.piggypiglet.gary.core.storage.mysql.tables.Users;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class MySQL {
    @Inject private GTypes gTypes;
    @Inject private Users users;
    @Inject private GaryBot garyBot;

    public void connect() {
        Thread thread = new Thread(() -> {
            DatabaseOptions options = DatabaseOptions.builder().mysql(
                    gTypes.getString("config", "mysql-username"),
                    gTypes.getString("config", "mysql-password"),
                    gTypes.getString("config", "mysql-db"),
                    gTypes.getString("config", "mysql-ip") + ":" + gTypes.getString("config", "mysql-port")
            ).build();

            Database db = PooledDatabaseOptions.builder().options(options).createHikariDatabase();
            DB.setGlobalDatabase(db);

            try {
                if (DB.getFirstRow("SHOW TABLES LIKE 'gary_users'") == null) {
                    Stream.of(
                            "users", "stats", "messages", "giveaways", "giveaways_users", "faq"
                    ).forEach(str -> DB.executeUpdateAsync(gTypes.getString(str, "file-content")));

                    garyBot.getJda().getGuildById(Constants.HELP_CHAT).getMembers().forEach(member -> this.users.addUser(member.getUser()));
                }

                LoggerFactory.getLogger("MySQL").info("database successfully loaded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.setName("MySQL");
        thread.start();
    }
}
