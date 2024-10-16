package org.paz.community.utils;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class TimeGetter {

    public TimeGetter(){

    }
    public String getFormattedCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss_"));
    }
}
