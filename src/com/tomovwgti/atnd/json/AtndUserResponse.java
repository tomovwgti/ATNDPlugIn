
package com.tomovwgti.atnd.json;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel
public class AtndUserResponse {

    @JsonKey("results_returned")
    public int resultsReturned;
    @JsonKey("results_available")
    public int resultsAvailable;
    @JsonKey
    public List<AtndUserResult> events;

    public int getResultsReturned() {
        return resultsReturned;
    }

    public void setResultsReturned(int resultsReturned) {
        this.resultsReturned = resultsReturned;
    }

    public int getResultsAvailable() {
        return resultsAvailable;
    }

    public void setResultsAvailable(int resultsAvailable) {
        this.resultsAvailable = resultsAvailable;
    }

    public List<AtndUserResult> getEvents() {
        return events;
    }

    public void setEvents(List<AtndUserResult> events) {
        this.events = events;
    }
}
