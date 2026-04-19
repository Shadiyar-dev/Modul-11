package com.ecommerce;

import java.util.*;
import java.time.LocalDateTime;

// ========== МОДЕЛИ ==========

// Абстрактная сущность User (Пользователь)
abstract class User {
    protected UUID id;
    protected String name;
    protected String email;
    protected String address;
    protected String phone;
    protected String role;

    public User(String name, String email, String address, String phone, String role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public abstract void register();
    public abstract void login();
    public abstract void updateData(String name, String email, String address, String phone);

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
}

// Класс Customer (Клиент) - наследуется от User
class Customer extends User {
    private double loyaltyPoints;
    private List<Order> orderHistory;
    private List<Review> reviews;

    public Customer(String name, String email, String address, String phone) {
        super(name, email, address, phone, "CUSTOMER");
        this.loyaltyPoints = 0;
        this.orderHistory = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    @Override
    public void register() {
        System.out.println("[OK] Клиент " + name + " успешно зарегистрирован!");
    }

    @Override
    public void login() {
        System.out.println("[AUTH] Клиент " + name + " вошел в систему!");
    }

    @Override
    public void updateData(String name, String email, String address, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        System.out.println("[UPDATE] Данные клиента обновлены!");
    }

    public void addLoyaltyPoints(double amount) {
        this.loyaltyPoints += amount;
        System.out.println("[LOYALTY] Начислено " + amount + " бонусных баллов. Всего: " + loyaltyPoints);
    }

    public boolean usePoints(int points) {
        if (points <= loyaltyPoints) {
            loyaltyPoints -= points;
            System.out.println("[LOYALTY] Использовано " + points + " баллов. Остаток: " + loyaltyPoints);
            return true;
        }
        System.out.println("[ERROR] Недостаточно баллов!");
        return false;
    }

    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public double getLoyaltyPoints() { return loyaltyPoints; }
    public List<Order> getOrderHistory() { return orderHistory; }
}

// Класс Admin (Администратор) - наследуется от User
class Admin extends User {
    private List<String> actionLog;

    public Admin(String name, String email, String address, String phone) {
        super(name, email, address, phone, "ADMIN");
        this.actionLog = new ArrayList<>();
    }

    @Override
    public void register() {
        System.out.println("[OK] Администратор " + name + " успешно зарегистрирован!");
    }

    @Override
    public void login() {
        System.out.println("[AUTH] Администратор " + name + " вошел в систему!");
    }

    @Override
    public void updateData(String name, String email, String address, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        System.out.println("[UPDATE] Данные администратора обновлены!");
    }

    public void logAction(String action) {
        String logEntry = LocalDateTime.now() + " - АДМИН " + name + ": " + action;
        actionLog.add(logEntry);
        System.out.println("[LOG] Действие записано: " + action);
    }

    public List<String> getActionLog() { return actionLog; }

    public void printActionLog() {
        System.out.println("\n=== ЖУРНАЛ ДЕЙСТВИЙ АДМИНИСТРАТОРА ===");
        actionLog.forEach(System.out::println);
    }
}

// Enum Category (Категория товара)
enum Category {
    ELECTRONICS("Электроника"),
    CLOTHING("Одежда"),
    BOOKS("Книги"),
    HOME("Товары для дома");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}

// Enum OrderStatus (Статус заказа)
enum OrderStatus {
    PROCESSING("В обработке"),
    SHIPPED("В доставке"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменен");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}

// Enum PaymentType (Тип платежа)
enum PaymentType {
    CARD("Банковская карта"),
    E_WALLET("Электронный кошелек");

    private String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}

// Enum PaymentStatus (Статус платежа)
enum PaymentStatus {
    PENDING("В ожидании"),
    COMPLETED("Завершен"),
    FAILED("Ошибка"),
    REFUNDED("Возвращен");

    private String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}

// Enum DeliveryStatus (Статус доставки)
enum DeliveryStatus {
    PREPARING,
    IN_TRANSIT,
    DELIVERED;

    public String getDescription() {
        switch (this) {
            case PREPARING: return "Подготовка к отправке";
            case IN_TRANSIT: return "В пути";
            case DELIVERED: return "Доставлен";
            default: return "";
        }
    }
}

// Класс Product (Товар)
class Product {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private Category category;
    private double discount;

    public Product(String name, String description, double price, int stockQuantity, Category category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.discount = 0;
    }

    public void create() {
        System.out.println("[PRODUCT] Товар создан: " + name);
    }

    public void update() {
        System.out.println("[PRODUCT] Товар обновлен: " + name);
    }

    public void delete() {
        System.out.println("[PRODUCT] Товар удален: " + name);
    }

    public void applyDiscount(double percent) {
        this.discount = percent;
        System.out.println("[DISCOUNT] Скидка " + percent + "% применена к товару " + name);
    }

    public double getFinalPrice() {
        return price * (1 - discount / 100);
    }

    public boolean reduceStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public double getDiscount() { return discount; }
}

// Класс OrderItem (Позиция заказа)
class OrderItem {
    private Product product;
    private int quantity;
    private double priceAtPurchase;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = product.getFinalPrice();
    }

    public double getSubtotal() {
        return priceAtPurchase * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
}

// Класс PromoCode (Промокод)
class PromoCode {
    private String code;
    private double discountPercent;
    private LocalDateTime validUntil;
    private boolean isActive;

    public PromoCode(String code, double discountPercent, int validDays) {
        this.code = code;
        this.discountPercent = discountPercent;
        this.validUntil = LocalDateTime.now().plusDays(validDays);
        this.isActive = true;
    }

    public boolean apply() {
        if (isActive && LocalDateTime.now().isBefore(validUntil)) {
            System.out.println("[PROMO] Промокод " + code + " применен! Скидка " + discountPercent + "%");
            return true;
        }
        System.out.println("[ERROR] Промокод просрочен или недействителен!");
        return false;
    }

    public double getDiscountPercent() { return discountPercent; }
    public String getCode() { return code; }
}

// Класс Payment (Платеж)
class Payment {
    private UUID id;
    private PaymentType type;
    private double amount;
    private PaymentStatus status;
    private LocalDateTime date;

    public Payment(PaymentType type, double amount) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.date = LocalDateTime.now();
    }

    public void process() {
        System.out.println("[PAYMENT] Обработка платежа " + type.getDescription() + " на сумму $" + amount);
        this.status = PaymentStatus.COMPLETED;
        System.out.println("[PAYMENT] Платеж завершен!");
    }

    public void refund() {
        System.out.println("[PAYMENT] Возврат средств $" + amount);
        this.status = PaymentStatus.REFUNDED;
        System.out.println("[PAYMENT] Возврат выполнен!");
    }

    public UUID getId() { return id; }
    public PaymentType getType() { return type; }
    public double getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
}

// Класс Delivery (Доставка)
class Delivery {
    private UUID id;
    private String address;
    private DeliveryStatus status;
    private String courier;
    private LocalDateTime estimatedDelivery;

    public Delivery(String address, String courier) {
        this.id = UUID.randomUUID();
        this.address = address;
        this.courier = courier;
        this.status = DeliveryStatus.PREPARING;
        this.estimatedDelivery = LocalDateTime.now().plusDays(3);
    }

    public void send() {
        this.status = DeliveryStatus.IN_TRANSIT;
        System.out.println("[DELIVERY] Доставка отправлена через " + courier);
    }

    public void track() {
        System.out.println("[DELIVERY] Статус доставки: " + status.getDescription());
        System.out.println("[DELIVERY] Ожидаемая дата доставки: " + estimatedDelivery);
    }

    public void complete() {
        this.status = DeliveryStatus.DELIVERED;
        System.out.println("[DELIVERY] Доставка завершена!");
    }

    public UUID getId() { return id; }
    public DeliveryStatus getStatus() { return status; }
    public String getCourier() { return courier; }
}

// Класс Review (Отзыв)
class Review {
    private UUID id;
    private Customer customer;
    private Product product;
    private int rating;
    private String comment;
    private LocalDateTime date;

    public Review(Customer customer, Product product, int rating, String comment) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public void addReview() {
        System.out.println("[REVIEW] Отзыв добавлен для товара " + product.getName() + " от " + customer.getName());
        System.out.println("   Оценка: " + rating + "/5 - " + comment);
    }

    public int getRating() { return rating; }
}

// Класс Warehouse (Склад) - для поддержки нескольких складов
class Warehouse {
    private UUID id;
    private String location;
    private Map<Product, Integer> stock;

    public Warehouse(String location) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.stock = new HashMap<>();
    }

    public void addStock(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
        System.out.println("[WAREHOUSE] Добавлено " + quantity + " шт. товара " + product.getName() + " на склад " + location);
    }

    public boolean reserveStock(Product product, int quantity) {
        int available = stock.getOrDefault(product, 0);
        if (available >= quantity) {
            stock.put(product, available - quantity);
            System.out.println("[WAREHOUSE] Зарезервировано " + quantity + " шт. товара " + product.getName() + " на складе " + location);
            return true;
        }
        System.out.println("[ERROR] Недостаточно товара на складе " + location);
        return false;
    }

    public String getLocation() { return location; }
    public int getStock(Product product) { return stock.getOrDefault(product, 0); }
}

// Класс Order (Заказ)
class Order {
    private UUID id;
    private LocalDateTime creationDate;
    private OrderStatus status;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private Payment payment;
    private Delivery delivery;
    private PromoCode appliedPromoCode;

    public Order(Customer customer) {
        this.id = UUID.randomUUID();
        this.creationDate = LocalDateTime.now();
        this.status = OrderStatus.PROCESSING;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addItem(Product product, int quantity) {
        if (product.reduceStock(quantity)) {
            items.add(new OrderItem(product, quantity));
            calculateTotal();
            System.out.println("[ORDER] Добавлено " + quantity + "x " + product.getName() + " в заказ");
        } else {
            System.out.println("[ERROR] Недостаточно товара на складе для " + product.getName());
        }
    }

    public void applyPromoCode(PromoCode promoCode) {
        if (promoCode.apply()) {
            this.appliedPromoCode = promoCode;
            calculateTotal();
        }
    }

    private void calculateTotal() {
        totalAmount = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
        if (appliedPromoCode != null) {
            totalAmount *= (1 - appliedPromoCode.getDiscountPercent() / 100);
        }
    }

    public void place() {
        if (items.isEmpty()) {
            System.out.println("[ERROR] Нельзя оформить пустой заказ!");
            return;
        }
        this.status = OrderStatus.PROCESSING;
        System.out.println("[ORDER] Заказ успешно оформлен!");
        System.out.println("[ORDER] Общая сумма: $" + String.format("%.2f", totalAmount));

        double loyaltyBonus = totalAmount * 0.05;
        customer.addLoyaltyPoints(loyaltyBonus);
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        if (payment != null) {
            payment.refund();
        }
        System.out.println("[ORDER] Заказ отменен!");
    }

    public void pay(PaymentType type) {
        this.payment = new Payment(type, totalAmount);
        payment.process();
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void complete() {
        this.status = OrderStatus.DELIVERED;
        if (delivery != null) {
            delivery.complete();
        }
        System.out.println("[ORDER] Заказ завершен! Спасибо за покупку!");
    }

    public UUID getId() { return id; }
    public OrderStatus getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderItem> getItems() { return items; }
    public Customer getCustomer() { return customer; }
}

// ========== ПАТТЕРН "ФАБРИКА" ==========

class ProductFactory {
    public static Product createProduct(String name, String description, double price, int stock, Category category) {
        Product product = new Product(name, description, price, stock, category);

        switch (category) {
            case ELECTRONICS:
                product.applyDiscount(5);
                break;
            case CLOTHING:
                product.applyDiscount(10);
                break;
            default:
                break;
        }

        product.create();
        return product;
    }
}

// ========== СЕРВИСЫ ==========

class LoyaltyService {
    public static void applyBirthdayBonus(Customer customer) {
        customer.addLoyaltyPoints(100);
        System.out.println("[LOYALTY] С днем рождения! Вы получили 100 бонусных баллов!");
    }
}

// ========== ГЛАВНЫЙ КЛАСС ==========

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ РАБОТЫ ИНТЕРНЕТ-МАГАЗИНА ===\n");

        // 1. Регистрация пользователей
        System.out.println("--- 1. РЕГИСТРАЦИЯ ПОЛЬЗОВАТЕЛЕЙ ---");
        Customer john = new Customer("Иван Петров", "ivan@email.com", "ул. Ленина, д. 10", "+77001234567");
        Admin admin = new Admin("Алия Админ", "admin@shop.com", "пр. Абая, д. 5", "+77009876543");

        john.register();
        admin.register();
        System.out.println();

        // 2. Логирование действий администратора
        System.out.println("--- 2. ЖУРНАЛ ДЕЙСТВИЙ АДМИНИСТРАТОРА ---");
        admin.login();
        admin.logAction("Создал новую категорию товаров");
        admin.logAction("Обновил цены на электронику");
        System.out.println();

        // 3. Создание товаров через фабрику
        System.out.println("--- 3. СОЗДАНИЕ ТОВАРОВ ---");
        Product laptop = ProductFactory.createProduct("Игровой ноутбук", "Мощный игровой ноутбук", 120000, 10, Category.ELECTRONICS);
        Product tshirt = ProductFactory.createProduct("Футболка хлопок", "Удобная хлопковая футболка", 5000, 50, Category.CLOTHING);
        Product book = ProductFactory.createProduct("Java программирование", "Изучение Java с нуля", 8000, 30, Category.BOOKS);
        System.out.println();

        // 4. Создание заказа
        System.out.println("--- 4. СОЗДАНИЕ ЗАКАЗА ---");
        Order order1 = new Order(john);
        order1.addItem(laptop, 1);
        order1.addItem(tshirt, 2);
        System.out.println();

        // 5. Применение промокода
        System.out.println("--- 5. ПРИМЕНЕНИЕ ПРОМОКОДА ---");
        PromoCode promo = new PromoCode("СКИДКА20", 20, 7);
        order1.applyPromoCode(promo);
        System.out.println();

        // 6. Оформление заказа
        System.out.println("--- 6. ОФОРМЛЕНИЕ ЗАКАЗА ---");
        order1.place();
        System.out.println();

        // 7. Оплата
        System.out.println("--- 7. ОПЛАТА ---");
        order1.pay(PaymentType.CARD);
        System.out.println();

        // 8. Доставка
        System.out.println("--- 8. ДОСТАВКА ---");
        Delivery delivery1 = new Delivery(john.getAddress(), "КазПочта");
        order1.setDelivery(delivery1);
        delivery1.send();
        delivery1.track();
        System.out.println();

        // 9. Отзыв о товаре
        System.out.println("--- 9. ОТЗЫВЫ ---");
        Review review = new Review(john, laptop, 5, "Отличный ноутбук! Очень доволен покупкой!");
        review.addReview();
        System.out.println();

        // 10. Работа со складами
        System.out.println("--- 10. УПРАВЛЕНИЕ СКЛАДАМИ ---");
        Warehouse warehouse1 = new Warehouse("Алматы");
        Warehouse warehouse2 = new Warehouse("Астана");
        warehouse1.addStock(laptop, 5);
        warehouse2.addStock(laptop, 3);
        warehouse1.reserveStock(laptop, 2);
        System.out.println();

        // 11. Система лояльности
        System.out.println("--- 11. СИСТЕМА ЛОЯЛЬНОСТИ ---");
        LoyaltyService.applyBirthdayBonus(john);
        john.usePoints(50);
        System.out.println();

        // 12. Завершение заказа
        System.out.println("--- 12. ЗАВЕРШЕНИЕ ЗАКАЗА ---");
        order1.complete();
        System.out.println();

        // 13. Логи администратора
        System.out.println("--- 13. ПРОСМОТР ЖУРНАЛА АДМИНИСТРАТОРА ---");
        admin.printActionLog();

        System.out.println("\n=== ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
    }
}