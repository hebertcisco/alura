//
//  StoreTypeTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class StoreTypeTests: XCTestCase {
    
    // MARK: - Attributes
    
    var sut: StoreType!
    
    // MARK: - Setup
    
    override func setUpWithError() throws {
        sut = StoreType(
            id: 1,
            name: "Monstro Burger",
            logoImage: "logo-monstro-burger",
            headerImage: "header-monstro-burger",
            location: "Rua Principal, 123, São Paulo, SP",
            stars: 4,
            products: [],
            specialties: ["hamburguer", "lanchonete"]
        )
    }
    
    override func tearDownWithError() throws {
        sut = nil
    }
    
    // MARK: - Unit Tests
    
    func testStoreTypeInitialization() {
        XCTAssertEqual(1, sut.id)
        XCTAssertEqual("Monstro Burger", sut.name)
        XCTAssertEqual("logo-monstro-burger", sut.logoImage)
        XCTAssertEqual("header-monstro-burger", sut.headerImage)
        XCTAssertEqual("Rua Principal, 123, São Paulo, SP", sut.location)
        XCTAssertEqual(4, sut.stars)
        XCTAssertEqual(0, sut.products?.count)
        XCTAssertEqual(2, sut.specialties?.count)
    }
    
    func testSearchableByName() {
        // Should match by name prefix
        XCTAssertTrue(sut.matches(query: "mon"))
        XCTAssertTrue(sut.matches(query: "monstro"))
        XCTAssertTrue(sut.matches(query: "m"))
    }
    
    func testSearchableByNameCaseInsensitive() {
        // Should be case insensitive
        XCTAssertTrue(sut.matches(query: "MON"))
        XCTAssertTrue(sut.matches(query: "MoNsTrO"))
    }
    
    func testSearchableBySpecialty() {
        // Should match by specialty prefix
        XCTAssertTrue(sut.matches(query: "hamb"))
        XCTAssertTrue(sut.matches(query: "lanchonete"))
        XCTAssertTrue(sut.matches(query: "lanch"))
    }
    
    func testSearchableNoMatch() {
        // Should not match when prefix doesn't exist
        XCTAssertFalse(sut.matches(query: "pizza"))
        XCTAssertFalse(sut.matches(query: "sushi"))
        XCTAssertFalse(sut.matches(query: "xyz"))
    }
    
    func testSearchableWithNoSpecialties() {
        let store = StoreType(
            id: 2,
            name: "Test Store",
            logoImage: nil,
            headerImage: nil,
            location: "Test Location",
            stars: 3,
            products: [],
            specialties: nil
        )
        
        // Should still match by name when no specialties
        XCTAssertTrue(store.matches(query: "test"))
        
        // Should not match by non-existent specialty
        XCTAssertFalse(store.matches(query: "pizza"))
    }
    
    func testSearchableWithEmptyQuery() {
        // Empty string is a prefix of everything
        XCTAssertTrue(sut.matches(query: ""))
    }
    
    func testDecodableConformance() throws {
        let json = """
        {
            "id": 1,
            "name": "Monstro Burger",
            "logo_image": "logo-monstro-burger",
            "header_image": "header-monstro-burger",
            "location": "Rua Principal, 123, São Paulo, SP",
            "stars": 4,
            "products": [],
            "specialties": ["hamburguer", "lanchonete"]
        }
        """
        
        let jsonData = Data(json.utf8)
        let decoder = JSONDecoder()
        
        let decodedStore = try decoder.decode(StoreType.self, from: jsonData)
        
        XCTAssertEqual(1, decodedStore.id)
        XCTAssertEqual("Monstro Burger", decodedStore.name)
        XCTAssertEqual("logo-monstro-burger", decodedStore.logoImage)
        XCTAssertEqual("header-monstro-burger", decodedStore.headerImage)
        XCTAssertEqual("Rua Principal, 123, São Paulo, SP", decodedStore.location)
        XCTAssertEqual(4, decodedStore.stars)
        XCTAssertEqual(2, decodedStore.specialties?.count)
    }
    
    func testDecodableWithNilValues() throws {
        let json = """
        {
            "id": 2,
            "name": "Simple Store",
            "location": "Test Location",
            "stars": 3,
            "products": []
        }
        """
        
        let jsonData = Data(json.utf8)
        let decoder = JSONDecoder()
        
        let decodedStore = try decoder.decode(StoreType.self, from: jsonData)
        
        XCTAssertEqual(2, decodedStore.id)
        XCTAssertEqual("Simple Store", decodedStore.name)
        XCTAssertNil(decodedStore.logoImage)
        XCTAssertNil(decodedStore.headerImage)
        XCTAssertNil(decodedStore.specialties)
    }
}
