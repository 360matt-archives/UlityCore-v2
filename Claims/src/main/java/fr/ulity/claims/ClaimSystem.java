package fr.ulity.claims;

import de.leonhard.storage.sections.FlatFileSection;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class ClaimSystem {

    public static String getChunkID (Player player){
        return getChunkID(player.getLocation());
    }

    public static String getChunkID (Location loc){
        Chunk chunk = loc.getChunk();

        Location minLoc = chunk.getBlock(0, 1, 0).getLocation();
        Location maxLoc = chunk.getBlock(15, 1, 15).getLocation();

        String minID = minLoc.getBlockX() + String.valueOf(minLoc.getBlockZ());
        String maxID = maxLoc.getBlockX() + String.valueOf(maxLoc.getBlockZ());

        return minID + maxID;

    }

    public static boolean oppositeClaimed (Player player){
        Location center = player.getLocation().getChunk().getBlock(7, 1, 7).getLocation();

        Location[] locList = new Location[]{
            center.clone().add(9, 0, 0),
                center.clone().subtract(9, 0, 0),
                center.clone().add(0, 0, 9),
                center.clone().subtract(0, 0, 9),

                center.clone().add(9, 0, 0).subtract(0, 0, 9),
                center.clone().add(0, 0, 9).subtract(9, 0, 0),
                center.clone().subtract(9, 0, 9),
                center.clone().add(9, 0, 9)
        };

        for (Location x : locList){
            if (canGetInfo(x).success)
                if (!getOwner(x).data.equals(player.getName()))
                    return true;
        }

        return false;
    }


    public static Status create (Player player){
        Status response = new Status();

        String id = getChunkID(player);
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());
        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");


        int limite = MainClaimBukkit.config.getInt("claims.max_owns");

        if (ownerList.size() >= limite)
            response.setStatus(false, "limit", limite);
        else if (ownerList.contains(id))
            response.setStatus(false, "already exist");
        else if (allClaims.contains(id))
            response.setStatus(false, "already taked");
        else if (oppositeClaimed(player))
            response.setStatus(false, "claim near");
        else {

            /* economy


             */



            ownerList.add(id);
            MainClaimBukkit.data.set("players." + player.getName(), ownerList);

            allClaims.set(id + ".owner", player.getName());
            allClaims.set(id + ".trusted", new ArrayList<>());
        }

        return response;

    }

    public static Status remove (Player player){
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");

        String id = getChunkID(player);
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());

        if (!allClaims.contains(id))
            response.setStatus(false, "no exist");
        else if (!ownerList.contains(id))
            response.setStatus(false, "no ownership");
        else{
            ownerList.remove(id);
            MainClaimBukkit.data.set("players." + player.getName(), ownerList);

            allClaims.remove(id);
        }

        return response;
    }


    public static Status addTrust (Player player, String trusted) {
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");
        String id = getChunkID(player);
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());

        if (!allClaims.contains(id))
            response.setStatus(false, "no exist");
        else if (!ownerList.contains(id))
            response.setStatus(false, "no ownership");
        else if (player.getName().equals(trusted))
            response.setStatus(false, "himself");
        else{
            List<String> trustedList = allClaims.getStringList(id + ".trusted");

            if (trustedList.contains(trusted))
                response.setStatus(false, "already trusted");
            else {
                trustedList.add(trusted);
                MainClaimBukkit.data.set("claims." + id + ".trusted", trustedList);
            }
        }

        return response;
    }
    public static Status removeTrust (Player player, String trusted) {
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");
        String id = getChunkID(player);
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());

        if (!allClaims.contains(id))
            response.setStatus(false, "no exist");
        else if (!ownerList.contains(id))
            response.setStatus(false, "no ownership");
        else if (player.getName().equals(trusted))
            response.setStatus(false, "himself");
        else{
            List<String> trustedList = allClaims.getStringList(id + ".trusted");

            if (!trustedList.contains(trusted))
                response.setStatus(false, "already untrusted");
            else {
                trustedList.remove(trusted);
                MainClaimBukkit.data.set("claims." + id + ".trusted", trustedList);
            }
        }

        return response;
    }


    public static Status removeAllTrust (Player player) {
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");
        String id = getChunkID(player);
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());

        if (!allClaims.contains(id))
            response.setStatus(false, "no exist");
        else if (!ownerList.contains(id))
            response.setStatus(false, "no ownership");
        else{
            List<String> trustedList = allClaims.getStringList(id + ".trusted");

            int count = trustedList.size();

            if (count < 1)
                response.setStatus(false, "none");
            else{
                response.setStatus(true, "success", count);
                MainClaimBukkit.data.set("claims." + id + ".trusted", trustedList);
            }



        }

        return response;
    }







    public static Status canGetInfo (Player player) {
        return canGetInfo(player.getLocation());
    }

    public static Status canGetInfo (Location loc) {
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");
        String id = getChunkID(loc);

        if (!allClaims.contains(id))
            response.setStatus(false, "no exist");

        return response;
    }



    public static Status getOwner (Player player) {
        return getOwner(player.getLocation());
    }

    public static Status getOwner (Location loc) {
        Status response = new Status();

        String id = getChunkID(loc);

        if (canGetInfo(loc).success) {
            String owner = MainClaimBukkit.data.getSection("claims." + id).getString("owner");
            response.setStatus(true, "success", owner);
        }
        else
            response.setStatus(false, "can't get info");


        return response;
    }

    public static Status getTrusts (Player player) {
        return getTrusts(player.getLocation());
    }

    public static Status getTrusts (Location loc) {
        Status response = new Status();

        String id = getChunkID(loc);

        if (canGetInfo(loc).success) {
            ArrayList trusts = (ArrayList) MainClaimBukkit.data.get("claims." + id + ".trusted");
            response.setStatus(true, "success", trusts);
        }
        else
            response.setStatus(false, "can't get info");

        return response;
    }




    public static Status removeAllOfPlayer (Player player) {
        Status response = new Status();

        FlatFileSection allClaims = MainClaimBukkit.data.getSection("claims");
        List<String> ownerList = MainClaimBukkit.data.getStringList("players." + player.getName());

        int count = 0;
        for (String x : ownerList) {
            allClaims.remove(x);
            count++;
        }

        ownerList.clear();
        MainClaimBukkit.data.set("players." + player.getName(), ownerList);

        if (count == 0)
            response.setStatus(false, "none", 0);
        else
            response.setStatus(true, "success", count);

        return response;
    }


    public static class Status {
        public Boolean success = true;
        public String code = "success";
        public Object data = null;

        private void setStatus(Boolean success, String err) {
            this.success = success;
            this.code = err;
        }

        private void setStatus(Boolean success, String err, Object data) {
            this.success = success;
            this.code = err;
            this.data = data;
        }
    }

}

