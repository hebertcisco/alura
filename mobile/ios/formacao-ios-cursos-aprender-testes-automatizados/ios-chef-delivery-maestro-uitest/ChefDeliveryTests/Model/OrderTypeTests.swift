//
//  OrderTypeTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class OrderTypeTests: XCTestCase {
    
    // MARK: - Attributes
    
    var sut: OrderType!
    
    // MARK: - Setup
    
    override func setUpWithError() throws {
        sut = OrderType(
            id: 1,
            name: "Restaurantes",
            image: "hamburguer"
        )
    }
    
    override func tearDownWithError() throws {
        sut = nil
    }
    
    // MARK: - Unit Tests
    
    func testOrderTypeInitialization() {
        XCTAssertEqual(1, sut.id)
        XCTAssertEqual("Restaurantes", sut.name)
        XCTAssertEqual("hamburguer", sut.image)
    }
    
    func testIdentifiableConformance() {
        let order1 = OrderType(id: 1, name: "Order 1", image: "image1")
        let order2 = OrderType(id: 2, name: "Order 2", image: "image2")
        let order3 = OrderType(id: 1, name: "Order 3", image: "image3")
        
        // Orders with same ID should be considered the same
        XCTAssertEqual(order1.id, order3.id)
        
        // Orders with different IDs should be different
        XCTAssertNotEqual(order1.id, order2.id)
    }
    
    func testMultipleOrderTypesWithDifferentIds() {
        let orders = [
            OrderType(id: 1, name: "Restaurantes", image: "hamburguer"),
            OrderType(id: 2, name: "Mercado", image: "mercado"),
            OrderType(id: 3, name: "Farmácia", image: "farmacia")
        ]
        
        XCTAssertEqual(3, orders.count)
        XCTAssertEqual(1, orders[0].id)
        XCTAssertEqual(2, orders[1].id)
        XCTAssertEqual(3, orders[2].id)
    }
}
