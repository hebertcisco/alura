//
//  StringExtension.swift
//  ChefDelivery
//
//  Created by ALURA on 03/05/24.
//

import Foundation

extension String: Searchable {
    func matches(query: String) -> Bool {
        return lowercased().hasPrefix(query)
    }
}
