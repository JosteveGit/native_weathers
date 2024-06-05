//
//  WeatherViewModelTests.swift
//  WeatherTests
//
//  Created by Josteve Adekanbi on 05/06/2024.
//

import Foundation

import XCTest
import Combine
@testable import Weather

class WeatherViewModelTests: XCTestCase {
    private var viewModel: WeatherViewModel!
    private var repository: MockWeatherRepository!
    private var cancellables: Set<AnyCancellable>!

    override func setUp() {
        super.setUp()
        repository = MockWeatherRepository()
        viewModel = WeatherViewModel(repository: repository)
        cancellables = []
    }

    override func tearDown() {
        viewModel = nil
        repository = nil
        cancellables = nil
        super.tearDown()
    }

    func testGetWeatherSuccess() {
        let expectation = self.expectation(description: "Weather fetched successfully")

        repository.weatherResponse = .success(WeatherResponse(weather: [Weather(description: "Sunny")], main: Main(temp: 25.0)))

        viewModel.$weatherState
            .dropFirst()
            .sink { state in
                switch state {
                case .success(let response):
                    XCTAssertEqual(response.weather.first?.description, "Sunny")
                    XCTAssertEqual(response.main.temp, 25.0)
                    expectation.fulfill()
                default:
                    break
                }
            }
            .store(in: &cancellables)

        viewModel.getWeather(city: "London", apiKey: "dummyApiKey")

        wait(for: [expectation], timeout: 2.0)
    }

    func testGetWeatherFailure() {
        let expectation = self.expectation(description: "Weather fetch failed")

        repository.weatherResponse = .failure(NSError(domain: "TestError", code: -1, userInfo: nil))

        viewModel.$weatherState
            .dropFirst()
            .sink { state in
                switch state {
                case .error(let message):
                    XCTAssertEqual(message, "Failed to load weather data: The operation couldn’t be completed. (TestError error -1.)")
                    expectation.fulfill()
                default:
                    break
                }
            }
            .store(in: &cancellables)

        viewModel.getWeather(city: "London", apiKey: "dummyApiKey")

        wait(for: [expectation], timeout: 2.0)
    }
}

class MockWeatherRepository: WeatherRepository {
    var weatherResponse: Result<WeatherResponse, Error>?

    override func getWeather(city: String, apiKey: String, completion: @escaping (Result<WeatherResponse, Error>) -> Void) {
        if let response = weatherResponse {
            completion(response)
        }
    }
}
