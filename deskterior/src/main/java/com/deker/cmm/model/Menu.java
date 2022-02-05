package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Menu {

    private String menuName;
    private String menuUrl;

    private List<Menu> menu;

}
