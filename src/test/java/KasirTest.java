import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import contoh.Item;
import contoh.Kasir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KasirTest {
    private Kasir cashierService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        cashierService = new Kasir();
        System.setOut(new PrintStream(outContent));
    }

    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddItem() {
        Item item = new Item("Apple", 1.0, 3);
        cashierService.addItem(item);

        assertEquals(1, cashierService.getItems().size());
        assertEquals(item, cashierService.getItems().get(0));
    }

    @Test
    public void testDisplayItems() {
        Item item1 = new Item("Apple", 1.0, 3);
        Item item2 = new Item("Banana", 0.5, 5);
        cashierService.addItem(item1);
        cashierService.addItem(item2);

        cashierService.displayItems();

        String expectedOutput = "Items in the cart:" + System.lineSeparator() +
                                "Apple - 3 x 1.0 = 3.0" + System.lineSeparator() +
                                "Banana - 5 x 0.5 = 2.5" + System.lineSeparator();
        
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }

    @Test
    public void testCalculateTotal() {
        Item item1 = new Item("Apple", 1.0, 3);
        Item item2 = new Item("Banana", 0.5, 5);
        cashierService.addItem(item1);
        cashierService.addItem(item2);

        double expectedTotal = 5.5;
        assertEquals(expectedTotal, cashierService.calculateTotal(), 0.001);
    }

    @Test
    public void testCheckout() {
        Item item1 = new Item("Apple", 1.0, 3);
        Item item2 = new Item("Banana", 0.5, 5);
        cashierService.addItem(item1);
        cashierService.addItem(item2);

        cashierService.checkout();

        String expectedOutput = "Items in the cart:" + System.lineSeparator() +
                                "Apple - 3 x 1.0 = 3.0" + System.lineSeparator() +
                                "Banana - 5 x 0.5 = 2.5" + System.lineSeparator() +
                                "Total amount: 5.5" + System.lineSeparator() +
                                "Thank you for your purchase!" + System.lineSeparator();

        assertEquals(expectedOutput.trim(), outContent.toString().trim());
        assertTrue(cashierService.getItems().isEmpty());
    }
}
