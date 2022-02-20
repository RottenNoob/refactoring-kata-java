package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

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

    private Body getEmptyBasket() {
        return new Body(new Item[] {}, "STANDARD_CUSTOMER");
    }


    private Item[] getBasketWithSingleItem() {
        Item tshirt = new Item("TSHIRT", 1);
        Item[] items = new Item[] {tshirt};
        return items;
    }
}
