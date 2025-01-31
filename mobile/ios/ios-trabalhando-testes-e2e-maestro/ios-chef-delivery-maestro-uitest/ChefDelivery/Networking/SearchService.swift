//
//  SearchService.swift
//  ChefDelivery
//
//  Created by Ândriu F Coelho on 21/04/24.
//

import Foundation

protocol SearchServiceProtocol {
    func fetchData() async throws -> Result<[StoreType], RequestError>
}

struct SearchService: SearchServiceProtocol {
    
    func fetchData() async throws -> Result<[StoreType], RequestError> {
        guard let url = URL(string: "https://private-11274d-chefdeliveryapi.apiary-mock.com/search") else {
            return .failure(.invalidURL)
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        do {
            let (data, response) = try await URLSession.shared.data(for: request)
            
            if let httpResponse = response as? HTTPURLResponse {
                guard httpResponse.statusCode == 200 else {
                    return .failure(.serverError(httpResponse.statusCode))
                }
            }
            
            let storesObjects = try JSONDecoder().decode([StoreType].self, from: data)
            return .success(storesObjects)
        } catch {
            return .failure(.errorRequest(error: error.localizedDescription))
        }
    }
    
}
