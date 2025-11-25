//
//  HomeServiceTests.swift
//  ChefDeliveryTests
//
//  Created by TDD Implementation
//

import XCTest
import Mocker
@testable import ChefDelivery

final class HomeServiceTests: XCTestCase {
    
    // MARK: - Attributes
    
    var sut: HomeService!
    
    // MARK: - Setup
    
    override func setUpWithError() throws {
        sut = HomeService()
    }
    
    override func tearDownWithError() throws {
        sut = nil
    }
    
    // MARK: - fetchData Tests
    
    func testFetchDataWithSuccess() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        
        let json = getStoresMock()
        let jsonData = Data(json.utf8)
        let mock = Mock(url: url, contentType: .json, statusCode: 200, data: [.get: jsonData])
        mock.register()
        
        let expectation = XCTestExpectation(description: "Fetch data with success")
        
        do {
            let result = try await sut.fetchData()
            switch result {
            case .success(let stores):
                XCTAssertEqual(2, stores.count)
                XCTAssertEqual("Monstro Burger", stores[0].name)
                XCTAssertEqual("Food Court", stores[1].name)
                expectation.fulfill()
            case .failure(_):
                XCTFail("Expected success, but got failure")
            }
        } catch {
            XCTFail("Unexpected error: \(error)")
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    func testFetchDataWithServerError() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        Mock(url: url, statusCode: 500, data: [.get: Data()], requestError: RequestError.serverError(500)).register()
        
        let expectation = XCTestExpectation(description: "Fetch data with server error")
        
        do {
            let result = try await sut.fetchData()
            switch result {
            case .success(_):
                XCTFail("Expected failure, but got success")
            case .failure(let error):
                // Should succeed since we got the expected error
                expectation.fulfill()
            }
        } catch {
            // URLSession throws error for 500
            expectation.fulfill()
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    func testFetchDataWithInvalidJSON() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        
        let invalidJson = "{ invalid json }"
        let jsonData = Data(invalidJson.utf8)
        let mock = Mock(url: url, contentType: .json, statusCode: 200, data: [.get: jsonData])
        mock.register()
        
        let expectation = XCTestExpectation(description: "Fetch data with invalid JSON")
        
        do {
            let result = try await sut.fetchData()
            XCTFail("Should have thrown decoding error")
        } catch {
            // Should throw decoding error
            expectation.fulfill()
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    func testFetchDataWithEmptyResponse() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        
        let json = "[]"
        let jsonData = Data(json.utf8)
        let mock = Mock(url: url, contentType: .json, statusCode: 200, data: [.get: jsonData])
        mock.register()
        
        let expectation = XCTestExpectation(description: "Fetch data with empty response")
        
        do {
            let result = try await sut.fetchData()
            switch result {
            case .success(let stores):
                XCTAssertEqual(0, stores.count)
                expectation.fulfill()
            case .failure(_):
                XCTFail("Expected success with empty array")
            }
        } catch {
            XCTFail("Unexpected error: \(error)")
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    // MARK: - confirmOrder Tests
    
    func testConfirmOrderWithSuccess() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        
        let responseJson = """
        {
            "message": "Order confirmed successfully"
        }
        """
        let jsonData = Data(responseJson.utf8)
        let mock = Mock(url: url, contentType: .json, statusCode: 200, data: [.post: jsonData])
        mock.register()
        
        let product = ProductType(
            id: 1,
            name: "X-Burger",
            description: "Delicious burger",
            image: "burger",
            price: 25.50
        )
        
        let expectation = XCTestExpectation(description: "Confirm order with success")
        
        do {
            let result = try await sut.confirmOrder(product: product)
            switch result {
            case .success(let response):
                XCTAssertNotNil(response)
                if let message = response?["message"] as? String {
                    XCTAssertEqual("Order confirmed successfully", message)
                }
                expectation.fulfill()
            case .failure(_):
                XCTFail("Expected success, but got failure")
            }
        } catch {
            XCTFail("Unexpected error: \(error)")
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    func testConfirmOrderWithServerError() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        Mock(url: url, statusCode: 500, data: [.post: Data()], requestError: RequestError.serverError(500)).register()
        
        let product = ProductType(
            id: 1,
            name: "X-Burger",
            description: "Delicious burger",
            image: "burger",
            price: 25.50
        )
        
        let expectation = XCTestExpectation(description: "Confirm order with server error")
        
        do {
            let result = try await sut.confirmOrder(product: product)
            switch result {
            case .success(_):
                XCTFail("Expected failure, but got success")
            case .failure(_):
                expectation.fulfill()
            }
        } catch {
            // URLSession throws error for 500
            expectation.fulfill()
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
    
    func testConfirmOrderWithInvalidResponse() async {
        let url = URL(string: "https://private-665e5-matheusperez.apiary-mock.com/home")!
        
        let invalidJson = "invalid json"
        let jsonData = Data(invalidJson.utf8)
        let mock = Mock(url: url, contentType: .json, statusCode: 200, data: [.post: jsonData])
        mock.register()
        
        let product = ProductType(
            id: 1,
            name: "X-Burger",
            description: "Delicious burger",
            image: "burger",
            price: 25.50
        )
        
        let expectation = XCTestExpectation(description: "Confirm order with invalid response")
        
        do {
            let result = try await sut.confirmOrder(product: product)
            XCTFail("Should have thrown error")
        } catch {
            expectation.fulfill()
        }
        
        await fulfillment(of: [expectation], timeout: 1)
    }
}

// MARK: - Mock Data Extension

extension HomeServiceTests {
    func getStoresMock() -> String {
        return """
        [
            {
                "id": 1,
                "name": "Monstro Burger",
                "logo_image": "logo-monstro-burger",
                "header_image": "header-monstro-burger",
                "location": "Rua Principal, 123, São Paulo, SP",
                "stars": 4,
                "products": [
                    {
                        "id": 1,
                        "name": "X-Burger",
                        "description": "Delicious burger",
                        "image": "burger",
                        "price": 25.50
                    }
                ],
                "specialties": ["hamburguer", "lanchonete"]
            },
            {
                "id": 2,
                "name": "Food Court",
                "logo_image": "logo-food-court",
                "header_image": "header-food-court",
                "location": "Avenida Secundária, 456, São Paulo, SP",
                "stars": 4,
                "products": [
                    {
                        "id": 2,
                        "name": "Pizza Margherita",
                        "description": "Classic pizza",
                        "image": "pizza",
                        "price": 30.00
                    }
                ],
                "specialties": ["pizza", "lanchonete"]
            }
        ]
        """
    }
}
