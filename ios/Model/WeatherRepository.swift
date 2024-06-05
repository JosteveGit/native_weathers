//
//  WeatherRepository.swift
//  Weather
//
//  Created by Josteve Adekanbi on 05/06/2024.
//

import Foundation

import Foundation

class WeatherRepository {
    private let baseURL = "https://api.openweathermap.org/data/2.5/weather"

    func getWeather(city: String, apiKey: String, completion: @escaping (Result<WeatherResponse, Error>) -> Void) {
        let urlString = "\(baseURL)?q=\(city)&appid=\(apiKey)"
        guard let url = URL(string: urlString) else {
            completion(.failure(NSError(domain: "Invalid URL", code: -1, userInfo: nil)))
            return
        }

        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }

            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: -1, userInfo: nil)))
                return
            }

            do {
                let weatherResponse = try JSONDecoder().decode(WeatherResponse.self, from: data)
                completion(.success(weatherResponse))
            } catch {
                completion(.failure(error))
            }
        }
        task.resume()
    }
}
