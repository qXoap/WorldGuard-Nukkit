package dev.xoapp.worldguard.library.serializer;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

public class Serializer {

    public static String posToString(Position position) {
        if (position == null) {
            return null;
        }

        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        String world = position.getLevel().getFolderName();

        return x + ":" + y + ":" + z + ":" + world;
    }

    public static Position stringToPos(String pos) {
        String[] data = pos.split(":");

        double x = Double.parseDouble(data[0]);
        double y = Double.parseDouble(data[1]);
        double z = Double.parseDouble(data[2]);

        Level world = Server.getInstance().getLevelByName(data[3]);

        return new Position(x, y, z, world);
    }
}
