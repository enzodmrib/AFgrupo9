package com.programacao.distribuida.eventos.webapi.eventos_webapi.entities.enums;

import java.util.Arrays;

import lombok.Getter;


public enum EventTypeEnum {
    THEATER("Teatro"), MOVIE_THEATER("Cinema"), SHOW("Show");

    @Getter
    private String label;

    EventTypeEnum(String label){
        this.label = label;
    }

    public EventTypeEnum fromLabel(String label){
        return Arrays.asList(EventTypeEnum.values()).stream().filter(et -> et.getLabel().equalsIgnoreCase(label)).findAny().orElse(null);
    }
}
