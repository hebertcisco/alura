//
//  ArrayExtension.swift
//  ChefDelivery
//
//  Created by ALURA on 03/05/24.
//

import Foundation

extension Array: Searchable where Element == String {
    func matches(query: String) -> Bool {
        return contains(where: { $0.lowercased().hasPrefix(query) })
    }
}
