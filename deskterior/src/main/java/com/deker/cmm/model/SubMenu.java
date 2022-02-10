package com.deker.cmm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SubMenu {

    private List<Menu> community;
    private List<Menu> market;

}


