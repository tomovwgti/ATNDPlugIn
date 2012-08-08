
package com.tomovwgti.eventatnd.json;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel
public class EventAtndEventResponse {
    static final String TAG = EventAtndEventResponse.class.getSimpleName();

    @JsonKey("results_returned")
    public int resultsReturned;
    @JsonKey("results_available")
    public int resultsAvailable;
    @JsonKey("results_start")
    public int resultsStart;
    @JsonKey
    public List<EventAtndEventTmp> events;

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

    public int getResultsStart() {
        return resultsStart;
    }

    public void setResultsStart(int resultsStart) {
        this.resultsStart = resultsStart;
    }

    public List<EventAtndEventTmp> getEvents() {
        return events;
    }

    public void setEvents(List<EventAtndEventTmp> events) {
        this.events = events;
    }
}
