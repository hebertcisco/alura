//
//  ArrayExtensionTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class ArrayExtensionTests: XCTestCase {
    
    // MARK: - Unit Tests
    
    func testMatchesWithStringArray() {
        let array = ["hamburguer", "lanchonete", "pizza"]
        
        XCTAssertTrue(array.matches(query: "hamb"))
        XCTAssertTrue(array.matches(query: "lanch"))
        XCTAssertTrue(array.matches(query: "piz"))
    }
    
    func testMatchesWithNoMatch() {
        let array = ["hamburguer", "lanchonete", "pizza"]
        
        XCTAssertFalse(array.matches(query: "sushi"))
        XCTAssertFalse(array.matches(query: "taco"))
        XCTAssertFalse(array.matches(query: "xyz"))
    }
    
    func testMatchesWithEmptyArray() {
        let array: [String] = []
        
        XCTAssertFalse(array.matches(query: "test"))
        XCTAssertFalse(array.matches(query: ""))
    }
    
    func testMatchesWithEmptyQuery() {
        let array = ["hamburguer", "lanchonete"]
        
        // Empty string is a prefix of every string
        XCTAssertTrue(array.matches(query: ""))
    }
    
    func testMatchesCaseInsensitive() {
        let array = ["Pizza", "Hamburguer", "Sushi"]
        
        // Should match regardless of case
        XCTAssertTrue(array.matches(query: "pizza"))
        XCTAssertTrue(array.matches(query: "PIZZA"))
        XCTAssertTrue(array.matches(query: "PiZzA"))
        XCTAssertTrue(array.matches(query: "ham"))
        XCTAssertTrue(array.matches(query: "HAM"))
    }
    
    func testMatchesWithSingleElement() {
        let array = ["hamburguer"]
        
        XCTAssertTrue(array.matches(query: "hamb"))
        XCTAssertFalse(array.matches(query: "pizza"))
    }
    
    func testMatchesWithMultipleMatches() {
        let array = ["hamburguer", "hamburgueria", "hambúrguer artesanal"]
        
        // Should return true if at least one matches
        XCTAssertTrue(array.matches(query: "hamb"))
    }
    
    func testMatchesOnlyMatchesPrefix() {
        let array = ["hamburguer", "lanchonete"]
        
        // Should not match if query is not a prefix
        XCTAssertFalse(array.matches(query: "burger"))
        XCTAssertFalse(array.matches(query: "nete"))
    }
    
    func testMatchesWithSpecialCharacters() {
        let array = ["café", "doces & salgados", "pizza"]
        
        XCTAssertTrue(array.matches(query: "caf"))
        XCTAssertTrue(array.matches(query: "doc"))
        XCTAssertFalse(array.matches(query: "&"))
    }
    
    func testMatchesWithAccentedCharacters() {
        let array = ["Japonês", "Chinês", "Tailandês"]
        
        XCTAssertTrue(array.matches(query: "japon"))
        XCTAssertTrue(array.matches(query: "chin"))
        XCTAssertTrue(array.matches(query: "tailan"))
    }
    
    func testMatchesExactMatch() {
        let array = ["pizza", "hamburguer"]
        
        XCTAssertTrue(array.matches(query: "pizza"))
        XCTAssertTrue(array.matches(query: "hamburguer"))
    }
}
