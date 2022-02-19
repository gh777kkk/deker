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
    private String menuImgUrl;

    private List<Menu> menu;
    private SubMenu subMenu;

}


