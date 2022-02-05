package com.deker.mkt.service;




import com.deker.mkt.mapper.ProductMapper;
import com.deker.mkt.model.DeliveryStatus;
import com.deker.mkt.model.DeliveryUpdate;
import com.deker.mkt.model.TrackingData;
import lombok.RequiredArgsConstructor;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductMapper productMapper;
    public final ProductService productService;



    private int level;
    private int compare;



    @Async
    @Scheduled(cron = "0 56 4 * * ?") // 매일 자정
    public void deliveryStatus() throws Exception {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName()+"start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"end");
        }

    }




    @Async
    @Scheduled(cron = "0 56 4 * * ?")
    public void deliveryComplete() throws ParseException {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName()+"start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"end");
        }

    }






    @Async
    @Scheduled(cron = "0 56 4 * * ?")
    public void deliveryCompleteAlarm() throws ParseException {
        for(int i=0; i<10; i++){
            System.out.println(Thread.currentThread().getName()+"start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"end");
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
