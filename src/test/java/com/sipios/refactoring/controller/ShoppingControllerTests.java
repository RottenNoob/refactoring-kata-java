package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(getEmptyBasket())
        );
    }

    @Test
    void should_return_0_for_emptyBasket() {
        Assertions.assertEquals("0.0", controller.getPrice(getEmptyBasket()));
    }

    @Test
    void should_return_30_for_single_tshirt_and_standard_customer() {
        Assertions.assertEquals("30.0", controller.getPrice(new Body(getBasketWithSingleItem(), "STANDARD_CUSTOMER")));
    }

    @Test
    void should_return_30_for_single_tshirt_and_premium_customer() {
        Assertions.assertEquals("27.0", controller.getPrice(new Body(getBasketWithSingleItem(), "PREMIUM_CUSTOMER")));
    }

    @Test
    void should_return_30_for_single_tshirt_and_platinum_customer() {
        Assertions.assertEquals("15.0", controller.getPrice(new Body(getBasketWithSingleItem(), "PLATINUM_CUSTOMER")));
    }

    @Test
    void should_return_190_for_small_basket_and_standard_customer() {
        Assertions.assertEquals("190.0", controller.getPrice(new Body(getSmallBasketWithRandomItems(), "STANDARD_CUSTOMER")));
    }

    @Test
    void should_return_171_for_small_basket_and_premium_customer() {
        Assertions.assertEquals("171.0", controller.getPrice(new Body(getSmallBasketWithRandomItems(), "PREMIUM_CUSTOMER")));
    }

    @Test
    void should_return_95_for_small_basket_and_platinum_customer() {
        Assertions.assertEquals("95.0", controller.getPrice(new Body(getSmallBasketWithRandomItems(), "PLATINUM_CUSTOMER")));
    }

    @Test
    void should_throw_bad_request_for_medium_basket_and_standard_customers() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(getMediumBasketWithRandomItems(), "STANDARD_CUSTOMER")),
            "Price (390.0) is too high for standard customer");
    }

    @Test
    void should_return_351_for_medium_basket_and_premium_customer() {
        Assertions.assertEquals("351.0", controller.getPrice(new Body(getMediumBasketWithRandomItems(), "PREMIUM_CUSTOMER")));
    }

    @Test
    void should_return_195_for_medium_basket_and_platinum_customer() {
        Assertions.assertEquals("195.0", controller.getPrice(new Body(getMediumBasketWithRandomItems(), "PLATINUM_CUSTOMER")));
    }

    @Test
    void should_throw_bad_request_for_large_basket_and_premium_customers() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(getLargeBasketWithRandomItems(), "PREMIUM_CUSTOMER")),
            "Price (801.0) is too high for premium customer");
    }

    @Test
    void should_return_445_for_large_basket_and_platinum_customer() {
        Assertions.assertEquals("445.0", controller.getPrice(new Body(getLargeBasketWithRandomItems(), "PLATINUM_CUSTOMER")));
    }

    @Test
    void should_throw_bad_request_for_huge_basket_and_platinum_customers() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(getHugeBasketWithRandomItems(), "PLATINUM_CUSTOMER")),
            "Price (3055.0) is too high for platinum customer");
    }

    @Test
    void should_throw_bad_request_for_unknown_customers() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(getHugeBasketWithRandomItems(), "UNKNOWN_CUSTOMER")));
    }

    private Body getEmptyBasket() {
        return new Body(new Item[] {}, "STANDARD_CUSTOMER");
    }


    private Item[] getBasketWithSingleItem() {
        Item tshirt = new Item("TSHIRT", 1);
        Item[] items = new Item[] {tshirt};
        return items;
    }

    private Item[] getSmallBasketWithRandomItems() {
        Item tshirt = new Item("TSHIRT", 3);
        Item dresses = new Item("DRESS", 2);
        Item[] items = new Item[] {tshirt, dresses};
        return items;
    }

    private Item[] getMediumBasketWithRandomItems() {
        Item tshirt = new Item("TSHIRT", 3);
        Item dresses = new Item("DRESS", 2);
        Item jackets = new Item("JACKET", 2);
        Item[] items = new Item[] {tshirt, dresses, jackets};
        return items;
    }

    private Item[] getLargeBasketWithRandomItems() {
        Item tshirt = new Item("TSHIRT", 8);
        Item dresses = new Item("DRESS", 5);
        Item jackets = new Item("JACKET", 4);
        Item[] items = new Item[] {tshirt, dresses, jackets};
        return items;
    }

    private Item[] getHugeBasketWithRandomItems() {
        Item tshirt = new Item("TSHIRT", 82);
        Item dresses = new Item("DRESS", 53);
        Item jackets = new Item("JACKET", 10);
        Item[] items = new Item[] {tshirt, dresses, jackets};
        return items;
    }
}
