package lk.sliit.se.espnsports.data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dinukshakandasamanage on 3/27/18.
 */

public class SportsEventResult {

    @SerializedName("sport_event")
    private SportsEvent sportEvent;

    @SerializedName("sport_event_status")
    private SportsEventResult sportsEventResult;

    public SportsEvent getSportsEvent() {
        return sportEvent;
    }

    public void setSportsEvent(SportsEvent sportEvent) {
        this.sportEvent = sportEvent;
    }

    public SportsEvent getSportEvent() {
        return sportEvent;
    }

    public void setSportEvent(SportsEvent sportEvent) {
        this.sportEvent = sportEvent;
    }

    public SportsEventResult getSportsEventResult() {
        return sportsEventResult;
    }

    public void setSportsEventResult(SportsEventResult sportsEventResult) {
        this.sportsEventResult = sportsEventResult;
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            if (this.getSportsEvent() != null)
                jsonObject.put("sport_event", this.getSportsEvent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static SportsEventResult fromJson(JSONObject jsonObject){
        SportsEventResult sportsEventResult = new SportsEventResult();
        try {
            sportsEventResult.setSportsEvent(SportsEvent.fromJson(jsonObject.getJSONObject("sport_event")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sportsEventResult;
    }
}
