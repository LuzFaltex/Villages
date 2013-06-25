package com.domsplace.DataManagers;

import com.domsplace.Utils.VillageSQLUtils;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VillageSQLManager {
    public static boolean SetupDatabase() {
        String stmt = "CREATE TABLE IF NOT EXISTS `VillagesPlayers` ("
                + "`VillagePlayerID` int(11) NOT NULL AUTO_INCREMENT,"
                + "`VillagePlayerName` VARCHAR(200) NOT NULL,"
                + "CONSTRAINT VillagesPlayerPrimaryKey PRIMARY KEY (`VillagePlayerID`)"
                + ");";
        if(!VillageSQLUtils.sqlQuery(stmt)) {
            return false;
        }
        
        stmt = "CREATE TABLE IF NOT EXISTS `Villages` ("
                + "`VillageID` int(11) NOT NULL AUTO_INCREMENT,"
                + "`VillageName` VARCHAR(200) NOT NULL,"
                + "`VillageDescription` VARCHAR(2048) NOT NULL,"
                + "`VillageCreateDate` DATETIME NOT NULL,"
                + "`VillageChunkX` int(11) NOT NULL,"
                + "`VillageChunkZ` int(11) NOT NULL,"
                + "`VillageWorld` VARCHAR(2048) NOT NULL,"
                + "`VillageSize` int(11) NOT NULL,"
                + "`VillageBank` int(11) NOT NULL,"
                + "`VillageMayorID` int(11) NOT NULL,"
                + "CONSTRAINT VillagesPrimaryKey PRIMARY KEY (`VillageID`)"
                + ");";
        if(!VillageSQLUtils.sqlQuery(stmt)) {
            return false;
        }
        
        stmt = "CREATE TABLE IF NOT EXISTS `VillagesResidents` ("
                + "`VillagePlayerID` int(11) NOT NULL, "
                + "`VillageID` int(11) NOT NULL, "
                + "CONSTRAINT VillageResidentsPrimaryKey PRIMARY KEY (`VillagePlayerID`)"
                + ");";
        if(!VillageSQLUtils.sqlQuery(stmt)) {
            return false;
        }
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            VillageSQLUtils.recordSQLPlayer(p);
        }
        
        return true;
    }
}