
package com.tomovwgti.atnd.json;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * ATND betaからのレスポンス
 * 
 * @author tomo
 */
@JsonModel
public class AtndEventResponse {
    static final String TAG = AtndEventResponse.class.getSimpleName();

    @JsonKey("results_returned")
    public int resultsReturned;
    @JsonKey("results_available")
    public int resultsAvailable;
    @JsonKey
    public List<AtndEventResult> events;

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

    public List<AtndEventResult> getEvents() {
        return events;
    }

    public void setEvents(List<AtndEventResult> events) {
        this.events = events;
    }
}
