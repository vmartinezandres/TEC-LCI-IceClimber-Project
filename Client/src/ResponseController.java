package Client.src;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Dictionary;
import java.util.Hashtable;

public class ResponseController {

    /*
    * Leer el objeto Json y obtiene un diccionario con el ID del npc y las coordenadas en las que se encuentra
    * */
    public Dictionary<String, int[]> getNpcs(JSONObject json)
    {
        Dictionary npcsDictionary = new Hashtable();

        JSONArray npcs = json.getJSONArray("npcs");

        int nNpcs = npcs.length();

        for (int i = 0; i<nNpcs; i++)
        {
            JSONObject npc = npcs.getJSONObject(i);
            npcsDictionary.put(npc.getString("id"), new int[]{npc.getInt("x"), npc.getInt("y")});
        }

        return npcsDictionary;
    }

    /*
     * Leer el objeto Json y obtiene un int de las vidas del jugador
     * */
    public int getPlayersLifes(JSONObject json, int jugador)
    {
        JSONArray players = json.getJSONArray("jugadores");

        JSONObject player = players.getJSONObject(jugador - 1);

        int lifes = player.getInt("lifes");

        return  lifes;
    }

    /*
     * Leer el objeto Json y obtiene un int del nivel del jugador
     * */
    public int getPlayersLevel(JSONObject json, int jugador)
    {
        JSONArray players = json.getJSONArray("jugadores");

        JSONObject player = players.getJSONObject(jugador - 1);

        int level = player.getInt("level");

        System.out.println("nivel "+ level );
        return  level;
    }

    /* Lee los puntos de los jugadores*/
    public int getPlayersPoints(JSONObject json, int jugador)
    {
        JSONArray players = json.getJSONArray("jugadores");

        JSONObject player = players.getJSONObject(jugador - 1);

        int lifes = player.getInt("points");


        return  lifes;
    }

}
