//
//  SearchStoreViewModelTests.swift
//  ChefDeliveryTests
//
//  Created by ALURA on 03/05/24.
//

import XCTest
@testable import ChefDelivery

final class SearchStoreViewModelTests: XCTestCase {
    
    // MARK: - Attributes
    
    var sut: SearchStoreViewModel!
    
    // MARK: - Setup

    override func setUpWithError() throws {
        sut = SearchStoreViewModel(service: SearchService())
        
        sut.storesType = [StoreType(id: 1,
                                    name: "Monstro Burger",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Rua Principal, 123, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["hamburguer", "lanchonete"]),
                          StoreType(id: 2,
                                    name: "Food Court",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Avenida Secundária, 456, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["pizza", "lanchonete"]),
                          StoreType(id: 3,
                                    name: "Carbron",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Rua Terceira, 789, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["tacos", "mexicana"]),
                          StoreType(id: 4,
                                    name: "Casa do Sushi",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Av. dos Sushis, 456, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["sushi", "Japonês"]),
                          StoreType(id: 5,
                                    name: "Sabor da Índia",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Av. dos Sushis, 456, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["Indiana", "curries"]),
                          StoreType(id: 6,
                                    name: "Sabor Tailandês",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Av. dos Sushis, 456, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["Cozinha tailandesa", "comida apimentada"]),
                          StoreType(id: 7,
                                    name: "Sabores Mediterrâneos",
                                    logoImage: nil,
                                    headerImage: nil,
                                    location: "Av. dos Sushis, 456, São Paulo, SP",
                                    stars: 4,
                                    products: [],
                                    specialties: ["Cozinha mediterrânea", "Grego"])
        ]
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    // MARK: - Unit tests

    func testFilteredStores() {
        sut.searchText = "Ca"
        
        var filteredStores: [StoreType] = []
        
        do {
            filteredStores = try sut.filteredStores()
            XCTAssertEqual(2, filteredStores.count)
            XCTAssertEqual("Carbron", filteredStores[0].name)
        } catch {
            XCTFail("Failed to search stores")
        }
    }
    
    func testFilteredStoresWithSpecialCharactersInSearchText() {
        var filteredStores: [StoreType] = []
        
        sut.searchText = "!@#$%"
        
        do {
            filteredStores = try sut.filteredStores()
            XCTFail("Failed to search")
        } catch {
            XCTAssertTrue(filteredStores.isEmpty)
        }
    }
    
    func testFilteredStoresUsingTerm() {
        sut.searchText = "pizza"
        
        var filteredStores: [StoreType] = []
        
        do {
            filteredStores = try sut.filteredStores()
            XCTAssertEqual(1, filteredStores.count)
            XCTAssertEqual("Food Court", filteredStores[0].name)
        } catch {
            XCTFail("Failed to search")
        }
    }
    
    func testFilteredStoresException() {
        sut.searchText = "xxZZz"
        
        XCTAssertThrowsError(try sut.filteredStores())
    }
    
    func testFetchDataWithSuccess() {
        let stub = StubSearchService()
        sut = SearchStoreViewModel(service: stub)
        
        let expectation = XCTestExpectation(description: "load restaurants list")
        
        sut
            .$storesType
            .dropFirst()
            .sink { stores in
                XCTAssertEqual(2, stores.count)
                XCTAssertEqual("Monstro Burger", stores[0].name)
                XCTAssertEqual("Food Court", stores[1].name)
                
                expectation.fulfill()
            }
            .store(in: &sut.cancellables)
        
        sut.fetchData()
        
        wait(for: [expectation], timeout: 1)
    }
    
    func testFetchDataWithFail() {
        let stub = StubSearchServiceFail()
        sut = SearchStoreViewModel(service: stub)
        
        let expectation = XCTestExpectation(description: "Should show error alert")
        sut
            .$showAlert
            .dropFirst()
            .sink { showAlert in
                XCTAssertTrue(showAlert)
                expectation.fulfill()
            }
            .store(in: &sut.cancellables)
        
        sut.fetchData()
        wait(for: [expectation], timeout: 1)
    }
}

class StubSearchService: SearchServiceProtocol {
    func fetchData() async throws -> Result<[StoreType], RequestError> {
        let stores: [StoreType] = [
            StoreType(id: 1,
                                        name: "Monstro Burger",
                                        logoImage: nil,
                                        headerImage: nil,
                                        location: "Rua Principal, 123, São Paulo, SP",
                                        stars: 4,
                                        products: [],
                                        specialties: ["hamburguer", "lanchonete"]),
                              StoreType(id: 2,
                                        name: "Food Court",
                                        logoImage: nil,
                                        headerImage: nil,
                                        location: "Avenida Secundária, 456, São Paulo, SP",
                                        stars: 4,
                                        products: [],
                                        specialties: ["pizza", "lanchonete"])
        ]
        
        return .success(stores)
    }
}

class StubSearchServiceFail: SearchServiceProtocol {
    func fetchData() async throws -> Result<[StoreType], RequestError> {
        return .failure(RequestError.errorRequest(error: "error 500"))
    }
}
