package com.grinder.domain.cart.model;

import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.model.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuOptions {
    private Menu menu;

    private int quantity;

    private List<Option> options;
}
