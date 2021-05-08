package com.sda.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LocationServiceTest {

    LocationService locationService;

    @BeforeEach
    void setUp() {
        LocationRepository locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
    }



    @Test
    void whenCreateNewLocation_giveProperParameters_thenCreateNewLocation() {
        //when region null
        Location newLocation1 = locationService.createNewLocation("cityname", null, "countryname", 20.21, 80.21);

        //then
        assertThat(newLocation1.getId()).isNotNull();
        assertThat(newLocation1.getCityname()).isEqualTo("cityname");
        assertThat(newLocation1.getRegion()).isNull();
        assertThat(newLocation1.getCountryname()).isEqualTo("countryname");
        assertThat(newLocation1.getLongitude()).isEqualTo(20.21);
        assertThat(newLocation1.getLatitude()).isEqualTo(80.21);

        //when region is not null
        Location newLocation2 = locationService.createNewLocation("cityname", "region", "countryname", 20.21, 80.21);

        //then
        assertThat(newLocation2.getId()).isNotNull();
        assertThat(newLocation2.getCityname()).isEqualTo("cityname");
        assertThat(newLocation2.getRegion()).isEqualTo("region");
        assertThat(newLocation2.getCountryname()).isEqualTo("countryname");
        assertThat(newLocation2.getLongitude()).isEqualTo(20.21);
        assertThat(newLocation2.getLatitude()).isEqualTo(80.21);

    }

    @Test
    void whenCreateNewLocation_givenNullValues_ThenThrowAnException() {
        //when
        Throwable throwable = catchThrowable(() -> locationService.createNewLocation(null, "region", null, null, null));

        //then
        assertThat(throwable).isExactlyInstanceOf(RuntimeException.class);
    }

    @Test
    void whenCreateNewLocation_givenBlanklValues_ThenThrowAnException() {
        //when
        Throwable throwable = catchThrowable(() -> locationService.createNewLocation("", "", "", 80.21, 20.21));

        //then
        assertThat(throwable).isExactlyInstanceOf(RuntimeException.class);
    }


    @Test
    void whenCreateNewLocation_givenLatAndLongOutOfRange_throwAnException() {

        //when latitude and longitude is out of range
        Throwable throwable = catchThrowable(() -> locationService.createNewLocation("cityname", "region", "countryname", 190.11, 100.11));

        //then
        assertThat(throwable).isExactlyInstanceOf(RuntimeException.class);
    }


}
