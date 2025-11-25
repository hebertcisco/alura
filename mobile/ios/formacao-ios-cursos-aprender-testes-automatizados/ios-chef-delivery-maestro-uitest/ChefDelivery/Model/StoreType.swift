//
//  StoreType.swift
//  ChefDelivery
//
//  Created by Giovanna Moeller on 03/06/23.
//

import Foundation

protocol Searchable {
    func matches(query: String) -> Bool
}

struct StoreType: Identifiable, Decodable {
    let id: Int
    let name: String
    let logoImage: String?
    let headerImage: String?
    let location: String
    let stars: Int
    let products: [ProductType]?
    let specialties: [String]?
    
    init(id: Int, name: String, logoImage: String?, headerImage: String?, location: String, stars: Int, products: [ProductType]?, specialties: [String]? = nil) {
        self.id = id
        self.name = name
        self.logoImage = logoImage
        self.headerImage = headerImage
        self.location = location
        self.stars = stars
        self.products = products
        self.specialties = specialties
    }
    
    
    private enum CodingKeys: String, CodingKey {
        case id, name, location, stars, products, specialties
        case logoImage = "logo_image"
        case headerImage = "header_image"
    }
}

extension StoreType: Searchable {
    func matches(query: String) -> Bool {
        guard let specialties = specialties else { return false }
        let parameters: [Searchable] = [name, specialties]
        
        return parameters.contains(where: { $0.matches(query: query.lowercased()) })
    }
}
