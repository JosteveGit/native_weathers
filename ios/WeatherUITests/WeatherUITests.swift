//
//  WeatherUITests.swift
//  WeatherUITests
//
//  Created by Josteve Adekanbi on 05/06/2024.
//

import XCTest
import Combine
import Foundation

@testable import Weather

class IntegrationTests: XCTestCase {
    private var viewModel: WeatherViewModel!
    private var repository: WeatherRepository!
    private var cancellables: Set<AnyCancellable>!

    override func setUp() {
        super.setUp()
        repository = WeatherRepository()
        viewModel = WeatherViewModel(repository: repository)
        cancellables = []
    }

    override func tearDown() {
        viewModel = nil
        repository = nil
        cancellables = nil
        super.tearDown()
    }

    func testIntegration() {
        let expectation = self.expectation(description: "Weather fetched successfully")

        viewModel.$weatherState
            .dropFirst()
            .sink { state in
                switch state {
                case .success(let response):
                    XCTAssertNotNil(response.weather.first?.description)
                    XCTAssertNotNil(response.main.temp)
                    expectation.fulfill()
                case .error:
                    XCTFail("Expected success, got failure")
                default:
                    break
                }
            }
            .store(in: &cancellables)

        viewModel.getWeather(city: "London", apiKey: "eb05a02728f5edd17985006b4c42c07a")

        wait(for: [expectation], timeout: 10.0)
    }
}
