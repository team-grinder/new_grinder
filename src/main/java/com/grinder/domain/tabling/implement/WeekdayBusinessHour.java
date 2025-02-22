package com.grinder.domain.tabling.implement;

import com.grinder.domain.cafe.model.CafeBusinessInfo;
import org.springframework.stereotype.Component;

@Component
public class WeekdayBusinessHour extends BusinessHourTemplate {

    @Override
    protected int calculateMaxGuests(Long cafeId, CafeBusinessInfo businessHour) {
        return businessHour.getMaxGuestsPerTime();
    }
}
