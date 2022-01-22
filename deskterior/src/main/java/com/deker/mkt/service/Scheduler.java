package com.deker.mkt.service;




import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.DeliveryStatus;
import com.deker.mkt.model.TrackingData;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductMapper productMapper;
    public final ProductService productService;

    private int level;
    //@Scheduled(cron = "0 0 0 * * ?") // 매월 15일 오전 10시 15분에 실행
    @Scheduled(fixedRate = 1000)
    public void deliveryStatus() throws Exception {
        DeliveryStatus ds = productMapper.selectDeliveryStatus();
        TrackingData td = productService.getTracking(ds.getDeliveryCode(),ds.getWaybill());

        level = td.getLevel();
        if(level==6){

        }


        //ds.getDeliveryCode(),ds.getWaybill()
        //TrackingData trackingData = getTracking(result.getDeliveryCode(),result.getWaybill());

        //System.out.println("current thread : {}"+ Thread.currentThread().getName());
    }

}

