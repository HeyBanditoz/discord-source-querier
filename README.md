# Discord Server Querier
This is a simple discord bot that can query and return various statuses of Source-based game servers.
Structure of the bot is heavily inspired on [Yui](https://github.com/DV8FromTheWorld/Yui)

## Using libraries
* [JDA](https://github.com/DV8FromTheWorld/JDA)
* [Steam Condenser](https://github.com/koraktor/steam-condenser-java)
* [Gson](https://github.com/google/gson)
* [JDA-Utilities](https://github.com/JDA-Applications/JDA-Utilities)

## Commands
* !status \<address\> Returns the server name, current number of players, max players, game, gamemode, and map.
* !players \<address\> Returns a paginated list of players.
* !bash \<command\> Runs a bash command. Requires the user to be in botOwner.

## Building
The project uses Gradle. To build a fat jar of the bot, run

```gradle shadowJar```

The jar will be placed in `build/libs/`