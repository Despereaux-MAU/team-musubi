## ERD
```mermaid
---
title: Delivery Service Diagram
---
erDiagram
    MEMBER {
        bigint id pk
        varchar email
        varchar username
        varchar nickname
        varchar password
        varchar role
        tinyint is_deleted
        varchar address
        varchar phone
        datetime created_At
        datetime updated_At
        
    }
    
    STORE {
        bigint id pk
        varchar name
        datetime open_time
        datetime close_time
        int min_price
        varchar category
        varchar address
        varchar license
        tinyint togo
        varchar status
        datetime created_At
        datetime updated_At
        datetime deleted_At
        bigint member_id
    }
    
    MENU {
        bigint id pk
        varchar name
        int price
        varchar description
        tinyint is_deleted
        datetime created_At
        datetime updated_At
        bigint store_id
    }
    
    DELIVERY { 
        bigint id pk
        varchar datails
        varchar status
        int total_price
        bigint member_id
        bigint store_id
        }
        
        DELIVERY_MENU {
            bigint id pk
            int quantity
            int price
            bigint order_id
            bigint menu_id
 }
 
 REVIEW {
     bigint id pk
     int score
     varchar comment
     datetime created_At
     datetime updated_At
     bigint store_id
     bigint member_id
 }
```
