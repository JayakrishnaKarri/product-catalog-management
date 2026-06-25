package com.example.productcatalog.model;

/**
 * Enumeration representing predefined product categories.
 * Ensures data consistency and provides validation for product categorization.
 */
public enum ProductCategory {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    HOME_GARDEN("Home & Garden"),
    SPORTS_OUTDOORS("Sports & Outdoors"),
    TOYS_GAMES("Toys & Games"),
    HEALTH_BEAUTY("Health & Beauty"),
    AUTOMOTIVE("Automotive"),
    FOOD_BEVERAGES("Food & Beverages"),
    OFFICE_SUPPLIES("Office Supplies");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get category by display name (case-insensitive)
     */
    public static ProductCategory fromDisplayName(String displayName) {
        for (ProductCategory category : values()) {
            if (category.displayName.equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + displayName);
    }
}