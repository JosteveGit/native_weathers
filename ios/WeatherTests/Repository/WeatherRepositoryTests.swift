
import XCTest
@testable import Weather

class WeatherRepositoryTests: XCTestCase {
    private var repository: WeatherRepository!
    private var mockURLSession: MockURLSession!

    override func setUp() {
        super.setUp()
        mockURLSession = MockURLSession()
        repository = WeatherRepository(urlSession: mockURLSession)
    }

    override func tearDown() {
        repository = nil
        mockURLSession = nil
        super.tearDown()
    }

    func testGetWeatherSuccess() {
        let expectation = self.expectation(description: "Weather fetched successfully")

        let weatherResponse = WeatherResponse(weather: [Weather(description: "Sunny")], main: Main(temp: 25.0))
        mockURLSession.nextData = try? JSONEncoder().encode(weatherResponse)

        repository.getWeather(city: "London", apiKey: "KEY") { result in
            switch result {
            case .success(let response):
                XCTAssertEqual(response.weather.first?.description, "Sunny")
                XCTAssertEqual(response.main.temp, 25.0)
                expectation.fulfill()
            case .failure:
                XCTFail("Expected success, got failure")
            }
        }

        wait(for: [expectation], timeout: 2.0)
    }

    func testGetWeatherFailure() {
        let expectation = self.expectation(description: "Weather fetch failed")

        mockURLSession.nextError = NSError(domain: "TestError", code: -1, userInfo: nil)

        repository.getWeather(city: "London", apiKey: "FAKE KEY") { result in
            switch result {
            case .success:
                XCTFail("Expected failure, got success")
            case .failure(let error):
                XCTAssertEqual(error.localizedDescription, "The operation couldn’t be completed. (TestError error -1.)")
                expectation.fulfill()
            }
        }

        wait(for: [expectation], timeout: 2.0)
    }
}

class MockURLSession: URLSession {
    var nextData: Data?
    var nextError: Error?

    override func dataTask(with url: URL, completionHandler: @escaping (Data?, URLResponse?, Error?) -> Void) -> URLSessionDataTask {
        let task = MockURLSessionDataTask()
        task.completionHandler = {
            completionHandler(self.nextData, nil, self.nextError)
        }
        return task
    }
}

class MockURLSessionDataTask: URLSessionDataTask {
    var completionHandler: (() -> Void)?
    
    

    override func resume() {
        completionHandler?()
    }
}
