package com.deker.mkt.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class ProductSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> idArr;
    private Set<String> checkArr;

}