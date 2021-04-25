package com.sda.weather;

import java.util.Scanner;

public class UserInterface {

    public void runAplication(){

        System.out.println("Welcom to 'Weather-Location' application");

        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println("What do you want to do?:");
            System.out.println("1. Add new location to database.");
            System.out.println("2. Show all locations.");
            System.out.println("3. Show informations about weather.");
            System.out.println("---------------------------------------");
            System.out.println("0. Close application;");

            int actionNumber = scan.nextInt();

            switch(actionNumber) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    break;
            }
        }
    }

    private void addNewLocation() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Type city name: \n");
        String cityname = scan.nextLine();
        System.out.print("Type region (optional): \n");
        String region = scan.nextLine();
        System.out.print("Type country name: \n");
        String countryname = scan.nextLine();


    }
}
