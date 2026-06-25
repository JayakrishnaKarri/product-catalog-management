package com.example.productcatalog.model;

/**
 * Enumeration representing the status of a product in the catalog.
 * Used to manage product lifecycle and visibility.
 */
public enum ProductStatus {
    /**
     * Product is active and available for purchase
     */
    ACTIVE,
    
    /**
     * Product is temporarily inactive but may be reactivated
     */
    INACTIVE,
    
    /**
     * Product has been permanently discontinued
     */
    DISCONTINUED
}