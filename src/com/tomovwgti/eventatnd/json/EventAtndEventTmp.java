
package com.tomovwgti.eventatnd.json;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel
public class EventAtndEventTmp {

    @JsonKey
    public List<EventAtndEventResult> event;

    public List<EventAtndEventResult> getEvent() {
        return event;
    }

    public void setEvent(List<EventAtndEventResult> event) {
        this.event = event;
    }
}
