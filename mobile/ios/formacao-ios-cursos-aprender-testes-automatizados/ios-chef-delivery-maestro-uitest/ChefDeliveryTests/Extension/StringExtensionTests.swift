//
//  StringExtensionTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class StringExtensionTests: XCTestCase {
    
    // MARK: - Unit Tests
    
    func testMatchesWithValidPrefix() {
        let text = "Monstro Burger"
        
        XCTAssertTrue(text.matches(query: "mon"))
        XCTAssertTrue(text.matches(query: "monstro"))
        XCTAssertTrue(text.matches(query: "m"))
    }
    
    func testMatchesWithInvalidPrefix() {
        let text = "Monstro Burger"
        
        XCTAssertFalse(text.matches(query: "burger"))
        XCTAssertFalse(text.matches(query: "burg"))
        XCTAssertFalse(text.matches(query: "x"))
    }
    
    func testMatchesWithEmptyQuery() {
        let text = "Test String"
        
        // Empty string is a prefix of every string
        XCTAssertTrue(text.matches(query: ""))
    }
    
    func testMatchesWithEmptyString() {
        let text = ""
        
        // Empty string matches empty query
        XCTAssertTrue(text.matches(query: ""))
        
        // Empty string doesn't match non-empty query
        XCTAssertFalse(text.matches(query: "test"))
    }
    
    func testMatchesCaseInsensitive() {
        let text = "Pizza Italiana"
        
        // Lowercased query on uppercased text
        XCTAssertTrue(text.matches(query: "pizza"))
        XCTAssertTrue(text.matches(query: "PIZZA"))
        XCTAssertTrue(text.matches(query: "PiZzA"))
    }
    
    func testMatchesWithSpecialCharacters() {
        let text = "Café & Doces"
        
        XCTAssertTrue(text.matches(query: "café"))
        XCTAssertTrue(text.matches(query: "caf"))
        XCTAssertFalse(text.matches(query: "&"))
        XCTAssertFalse(text.matches(query: "doces"))
    }
    
    func testMatchesWithNumbers() {
        let text = "123 Main Street"
        
        XCTAssertTrue(text.matches(query: "1"))
        XCTAssertTrue(text.matches(query: "123"))
        XCTAssertFalse(text.matches(query: "main"))
    }
    
    func testMatchesWithAccentedCharacters() {
        let text = "São Paulo"
        
        XCTAssertTrue(text.matches(query: "são"))
        XCTAssertTrue(text.matches(query: "sã"))
        XCTAssertFalse(text.matches(query: "paulo"))
    }
    
    func testMatchesExactMatch() {
        let text = "exact"
        
        XCTAssertTrue(text.matches(query: "exact"))
    }
    
    func testMatchesLongerQuery() {
        let text = "short"
        
        // Query longer than text should not match
        XCTAssertFalse(text.matches(query: "shorterlength"))
    }
}
