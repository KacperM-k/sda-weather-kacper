package com.sda.weather;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    LocationController locationController;
    Scanner scan = new Scanner(System.in);

    public UserInterface(LocationController locationController) {
        this.locationController = locationController;
    }

    public void runAplication() {

        System.out.println("Welcome to 'Weather-Location' application");
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("What do you want to do?:");
            System.out.println("1. Add new location to database.");
            System.out.println("2. Show all locations from database.");
            System.out.println("3. Show informations about weather.");
            System.out.println("---------------------------------------");
            System.out.println("0. Close application;");

            int actionNumber = scan.nextInt();

            switch (actionNumber) {
                case 1:
                    addNewLocation();
                    break;
                case 2:
                    showAllLocations();
                    break;
                case 3:
                    showInfoAboutWeather();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addNewLocation() {

        System.out.print("Type city name: \n");
        String cityname = scan.nextLine();
        System.out.print("Type region (optional): \n");
        String region = scan.nextLine();
        System.out.print("Type country name: \n");
        String countryname = scan.nextLine();
        System.out.print("Type longitude (-180 to 180): \n");
        Double longitude = scan.nextDouble();
        longitude = checkLongitude(longitude);
        System.out.print("Type latitude (-90 to 90): \n");
        Double latitude = scan.nextDouble();
        latitude = checkLatitude(latitude);

        String response = locationController.addNewLocation(cityname, region, countryname, longitude, latitude);
        System.out.println("New location added:\n" + response);
        System.out.println();
    }

    private Double checkLongitude(Double longitude) {
        if (longitude > -180.0 && longitude < 180.0) return longitude;

        while (longitude < -180.0 || longitude > 180.0) {
            System.out.println("Longitude is incorrect, type value in the range -180 to 180");
            longitude = scan.nextDouble();
        }
        return longitude;
    }

    private Double checkLatitude(Double latitude) {
        if (latitude > -90 && latitude < 90) return latitude;

        while (latitude < -90 || latitude > 90) {
            System.out.println("Latitude is incorrect, type value in the range -90 to 90");
            latitude = scan.nextDouble();
        }
        return latitude;
    }

    private void showAllLocations(){
        System.out.println("Lokalizacje znajdujące się w bazie danych: ");
        locationController.showAllLocations();
        System.out.println();
    }

    private void showInfoAboutWeather(){
        System.out.print("Type id: \n");
        Long id = scan.nextLong();
        System.out.print("Type city name: \n");
        String cityname = scan.nextLine();

        String response = locationController.showInfoAboutWeather(id, cityname);
        System.out.println("Information about weather: " + response);

    }


}

