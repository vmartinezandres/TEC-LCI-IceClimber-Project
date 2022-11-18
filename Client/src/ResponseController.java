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
     * Leer el objeto Json y obtiene un diccionario con el ID del npc y las coordenadas en las que se encuentra
     * */
    public Dictionary<String, int[]> getPlayers(JSONObject json)
    {
        Dictionary playersDictionary = new Hashtable();

        JSONArray players = json.getJSONArray("jugadores");

        int nJugadores = players.length();

        for (int i = 0; i<nJugadores; i++)
        {
            JSONObject player = players.getJSONObject(i);
            playersDictionary.put(player.getString("id"), new int[]{player.getInt("x"), player.getInt("y")});

        }

        return  playersDictionary;
    }

}
