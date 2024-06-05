//
//  WeatherResponse.swift
//  Weather
//
//  Created by Josteve Adekanbi on 05/06/2024.
//
import Foundation

struct WeatherResponse: Codable {
    let weather: [Weather]
    let main: Main
}

struct Weather: Codable {
    let description: String
}

struct Main: Codable {
    let temp: Double
}
