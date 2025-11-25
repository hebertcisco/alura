//
//  ProductTypeTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class ProductTypeTests: XCTestCase {
    
    // MARK: - Attributes
    
    var sut: ProductType!
    
    // MARK: - Setup
    
    override func setUpWithError() throws {
        sut = ProductType(
            id: 1,
            name: "X-Burger",
            description: "Delicious burger with cheese",
            image: "burger-image",
            price: 25.50
        )
    }
    
    override func tearDownWithError() throws {
        sut = nil
    }
    
    // MARK: - Unit Tests
    
    func testProductTypeInitialization() {
        XCTAssertEqual(1, sut.id)
        XCTAssertEqual("X-Burger", sut.name)
        XCTAssertEqual("Delicious burger with cheese", sut.description)
        XCTAssertEqual("burger-image", sut.image)
        XCTAssertEqual(25.50, sut.price)
    }
    
    func testFormattedPrice() {
        let formattedPrice = sut.formattedPrice
        XCTAssertEqual("R$ 25,50", formattedPrice)
    }
    
    func testFormattedPriceWithZeroDecimals() {
        let product = ProductType(
            id: 2,
            name: "Test Product",
            description: "Test",
            image: "test",
            price: 10.00
        )
        
        XCTAssertEqual("R$ 10,00", product.formattedPrice)
    }
    
    func testFormattedPriceWithSmallValue() {
        let product = ProductType(
            id: 3,
            name: "Cheap Product",
            description: "Test",
            image: "test",
            price: 0.99
        )
        
        XCTAssertEqual("R$ 0,99", product.formattedPrice)
    }
    
    func testFormattedPriceWithLargeValue() {
        let product = ProductType(
            id: 4,
            name: "Expensive Product",
            description: "Test",
            image: "test",
            price: 1234.56
        )
        
        XCTAssertEqual("R$ 1234,56", product.formattedPrice)
    }
    
    func testCodableConformance() throws {
        let json = """
        {
            "id": 1,
            "name": "X-Burger",
            "description": "Delicious burger with cheese",
            "image": "burger-image",
            "price": 25.50
        }
        """
        
        let jsonData = Data(json.utf8)
        let decoder = JSONDecoder()
        
        let decodedProduct = try decoder.decode(ProductType.self, from: jsonData)
        
        XCTAssertEqual(1, decodedProduct.id)
        XCTAssertEqual("X-Burger", decodedProduct.name)
        XCTAssertEqual("Delicious burger with cheese", decodedProduct.description)
        XCTAssertEqual("burger-image", decodedProduct.image)
        XCTAssertEqual(25.50, decodedProduct.price)
    }
    
    func testEncodableConformance() throws {
        let encoder = JSONEncoder()
        encoder.outputFormatting = .sortedKeys
        
        let encodedData = try encoder.encode(sut)
        let jsonString = String(data: encodedData, encoding: .utf8)
        
        XCTAssertNotNil(jsonString)
        XCTAssertTrue(jsonString!.contains("\"id\":1"))
        XCTAssertTrue(jsonString!.contains("\"name\":\"X-Burger\""))
        XCTAssertTrue(jsonString!.contains("\"price\":25.5"))
    }
}
