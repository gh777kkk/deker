package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Menu {

    private int menuId;
    private int menuParent;
    private String menuName;
    private String menuUrl;

    private List<Menu> menu;
    private List<Object> subMenu;
    private List<Menu> community;
    private List<Menu> market;

}
