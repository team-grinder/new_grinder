package com.grinder.domain.member.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TierType {
    MASTER("master"),
    DIAMOND("diamond"),
    PLATINUM("platinum"),
    GOLD("gold"),
    SILVER("silver");

    private final String value;
}
