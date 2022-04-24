package com.deker.mkt.service;




import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.DeliveryStatus;
import com.deker.mkt.model.DeliveryUpdate;
import com.deker.mkt.model.TrackingData;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductMapper productMapper;
    public final ProductService productService;



    private int level;
    private int compare;


    @Async
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    public void deliveryStatus() throws Exception {
        //운송장, 택배코드, 오더 테이블 id 값 가져오기
        List<DeliveryStatus> dsList = productMapper.selectDeliveryStatus();

        //각 row 당 택배 level 값 가져와서 order에 값 update
        for (DeliveryStatus ds : dsList) {
            TrackingData td = productService.getTracking(ds.getDeliveryCode(), ds.getWaybill());
            DeliveryUpdate du = new DeliveryUpdate();

            level = td.getLevel();
            du.setLevel(level);
            du.setId(ds.getOrderId());
            productMapper.updateOrderState(du);

            //배송 완료면 완료날짜에 날짜 update
            if (level == 6) {

                du.setId(ds.getOrderId());
                du.setTimeString(td.getTimeFormat());
                productMapper.updateCompletedDate(du);

            }
        }
    }



    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void deliveryComplete() throws ParseException {
        List<DeliveryUpdate> duList = productMapper.selectDeliveryComplete();

        for (DeliveryUpdate du : duList) {

            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateParser.parse(du.getTimeString());
            //System.out.println(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 7);  //7일 후
            Date dDate = cal.getTime(); //date로 변경


            Date myDate = new Date();   //현재 date
            String day = dateParser.format(myDate);
            Date today = dateParser.parse(day);  // 다시 date로 변경


            compare = dDate.compareTo(today); //오늘 날짜와 배송 완료 3일후 비교 같으면 0, 크면 0 보다 큼
            if(compare > 0 || compare == 0){

                productMapper.updateOrderConfirm(du);

            }



        }

    }





    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void deliveryCompleteAlarm() throws ParseException {
        List<DeliveryUpdate> duList = productMapper.selectDeliveryComplete();

        for (DeliveryUpdate du : duList) {

            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateParser.parse(du.getTimeString());
            //System.out.println(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 3);  //3일 후
            Date dDate = cal.getTime(); //date로 변경


            Date myDate = new Date();   //현재 date
            String day = dateParser.format(myDate);  //년,월,일로 포맷
            Date today = dateParser.parse(day);  // 다시 date로 변경


            compare = dDate.compareTo(today); //오늘 날짜와 배송 완료 3일후 비교 같으면 0, 크면 0 보다 큼
            if(compare > 0 || compare == 0){

                //알람
            }



        }

    }




}

/*
SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateParser.parse(timeString);
                //System.out.println(date);
                cal = Calendar.getInstance();
                cal.setTime(date);

                cal.add(Calendar.DATE, 3);  //3일 후

                LocalDateTime dateTime = LocalDateTime.now();

                String cron =toCron(String.valueOf(dateTime.getMinute()),
                        String.valueOf(dateTime.getHour()),
                        String.valueOf(dateTime.getDayOfMonth()),
                        String.valueOf(dateTime.getMonth()),
                        String.valueOf(dateTime.getDayOfWeek()), String.valueOf(dateTime.getYear()));

                @Scheduled(cron = "0 0 0 * * ?")



                public static String toCron(final String mins, final String hrs, final String dayOfMonth, final String month, final String dayOfWeek, final String year) {
        return String.format("%s %s %s %s %s %s", mins, hrs, dayOfMonth, month, dayOfWeek, year);



        //ds.getDeliveryCode(),ds.getWaybill()
            //TrackingData trackingData = getTracking(result.getDeliveryCode(),result.getWaybill());

            //System.out.println("current thread : {}"+ Thread.currentThread().getName());

 */
