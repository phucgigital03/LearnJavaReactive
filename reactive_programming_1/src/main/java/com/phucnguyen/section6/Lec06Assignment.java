package com.phucnguyen.section6;


import com.phucnguyen.common.Util;
import com.phucnguyen.section6.assignment.ExternalServiceClient;
import com.phucnguyen.section6.assignment.InventoryService;
import com.phucnguyen.section6.assignment.RevenueService;

/*
    Ensure that the external service is up and running!
*/
public class Lec06Assignment {


    public static void main(String[] args) {
        var client = new ExternalServiceClient();
        var inventoryService = new InventoryService();
        var revenueService = new RevenueService();

        client.orderStream()
                .subscribe(inventoryService::consume);

        client.orderStream()
                .subscribe(revenueService::consume);


        inventoryService.stream()
                .subscribe(Util.subscriber("inventory"));

        revenueService.stream()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSecondDuration(30);
    }

}
