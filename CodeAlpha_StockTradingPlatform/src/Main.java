import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    String name;
    double price;

    Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Holding {
    Stock stock;
    int shares;

    Holding(Stock stock, int shares) {
        this.stock = stock;
        this.shares = shares;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Stock> market = new ArrayList<Stock>();
        ArrayList<Holding> portfolio = new ArrayList<Holding>();
        double cash = 1000000.00;

        market.add(new Stock("DANGOTE", 500.00));
        market.add(new Stock("GTCO", 60.00));
        market.add(new Stock("AIRTEL", 1800.00));

        boolean running = true;
        while (running) {
            System.out.println("\n=== Simple Stock Trading ===");
            System.out.println("1. View market");
            System.out.println("2. Buy shares");
            System.out.println("3. Sell shares");
            System.out.println("4. View portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            String choice = input.nextLine();

            if (choice.equals("1")) {
                showMarket(market);
            } else if (choice.equals("2")) {
                showMarket(market);
                System.out.print("Enter stock name: ");
                Stock stock = findStock(market, input.nextLine());
                if (stock == null) {
                    System.out.println("That stock is not on the market list.");
                    continue;
                }
                System.out.print("How many shares do you want to buy? ");
                int amount = readShares(input);
                if (amount <= 0) {
                    System.out.println("Enter a number of shares greater than zero.");
                    continue;
                }
                double cost = stock.price * amount;
                if (cost > cash) {
                    System.out.println("You don't have enough cash for that purchase.");
                    continue;
                }
                cash = cash - cost;
                addShares(portfolio, stock, amount);
                System.out.printf("Bought %d %s share(s) for ₦%.2f.%n", amount, stock.name, cost);
            } else if (choice.equals("3")) {
                if (portfolio.size() == 0) {
                    System.out.println("You don't own any shares yet.");
                    continue;
                }
                showPortfolio(portfolio, cash);
                System.out.print("Enter stock name to sell: ");
                Stock stock = findStock(market, input.nextLine());
                Holding holding = findHolding(portfolio, stock);
                if (holding == null) {
                    System.out.println("You don't own that stock.");
                    continue;
                }
                System.out.print("How many shares do you want to sell? ");
                int amount = readShares(input);
                if (amount <= 0 || amount > holding.shares) {
                    System.out.println("Enter a positive number no greater than the shares you own.");
                    continue;
                }
                double earnings = stock.price * amount;
                cash = cash + earnings;
                holding.shares = holding.shares - amount;
                if (holding.shares == 0) portfolio.remove(holding);
                System.out.printf("Sold %d %s share(s) for ₦%.2f.%n", amount, stock.name, earnings);
            } else if (choice.equals("4")) {
                showPortfolio(portfolio, cash);
            } else if (choice.equals("5")) {
                running = false;
            } else {
                System.out.println("Please choose a number from 1 to 5.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void showMarket(ArrayList<Stock> market) {
        System.out.println("\nMarket prices:");
        for (int i = 0; i < market.size(); i++) {
            Stock stock = market.get(i);
            System.out.printf("%s - ₦%.2f per share%n", stock.name, stock.price);
        }
    }

    private static Stock findStock(ArrayList<Stock> market, String name) {
        for (int i = 0; i < market.size(); i++) {
            Stock stock = market.get(i);
            if (stock.name.equalsIgnoreCase(name.trim())) return stock;
        }
        return null;
    }

    private static Holding findHolding(ArrayList<Holding> portfolio, Stock stock) {
        if (stock == null) return null;
        for (int i = 0; i < portfolio.size(); i++) {
            if (portfolio.get(i).stock == stock) return portfolio.get(i);
        }
        return null;
    }

    private static void addShares(ArrayList<Holding> portfolio, Stock stock, int amount) {
        Holding holding = findHolding(portfolio, stock);
        if (holding == null) portfolio.add(new Holding(stock, amount));
        else holding.shares = holding.shares + amount;
    }

    private static void showPortfolio(ArrayList<Holding> portfolio, double cash) {
        double investmentValue = 0;
        System.out.println("\nYour portfolio:");
        if (portfolio.size() == 0) System.out.println("No shares owned.");
        for (int i = 0; i < portfolio.size(); i++) {
            Holding holding = portfolio.get(i);
            double value = holding.stock.price * holding.shares;
            investmentValue = investmentValue + value;
            System.out.printf("%s: %d share(s), worth ₦%.2f%n", holding.stock.name, holding.shares, value);
        }
        System.out.printf("Cash: ₦%.2f%n", cash);
        System.out.printf("Total portfolio value: ₦%.2f%n", cash + investmentValue);
    }

    private static int readShares(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException error) {
            return -1;
        }
    }
}
