//
//  DoubleExtensionTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
@testable import ChefDelivery

final class DoubleExtensionTests: XCTestCase {
    
    // MARK: - Unit Tests
    
    func testFormatPriceWithTwoDecimals() {
        let value: Double = 10.50
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("10,50", formattedValue)
    }
    
    func testFormatPriceWithZeroDecimals() {
        let value: Double = 10.00
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("10,00", formattedValue)
    }
    
    func testFormatPriceWithSmallValue() {
        let value: Double = 0.99
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("0,99", formattedValue)
    }
    
    func testFormatPriceWithLargeValue() {
        let value: Double = 1234.56
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("1234,56", formattedValue)
    }
    
    func testFormatPriceWithZero() {
        let value: Double = 0.00
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("0,00", formattedValue)
    }
    
    func testFormatPriceWithSingleDecimal() {
        let value: Double = 5.5
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("5,50", formattedValue)
    }
    
    func testFormatPriceWithThreeDecimals() {
        // Should round to 2 decimals
        let value: Double = 9.999
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("10,00", formattedValue)
    }
    
    func testFormatPriceWithNegativeValue() {
        let value: Double = -15.75
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("-15,75", formattedValue)
    }
    
    func testFormatPriceWithVeryLargeValue() {
        let value: Double = 99999.99
        let formattedValue = value.formatPrice()
        
        XCTAssertEqual("99999,99", formattedValue)
    }
}
