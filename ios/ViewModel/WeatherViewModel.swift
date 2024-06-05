import Foundation

import Combine

class WeatherViewModel: ObservableObject {
    @Published private(set) var weatherState: WeatherState = .loading
    
    private let repository: WeatherRepository
    private var cancellables: Set<AnyCancellable> = []
    
    init(repository: WeatherRepository) {
        self.repository = repository
    }
    
    func getWeather(city: String, apiKey: String) {
        self.weatherState = .loading
        repository.getWeather(city: city, apiKey: apiKey) { [weak self] result in
            switch result {
            case .success(let response):
                DispatchQueue.main.async {
                    self?.weatherState = .success(response)
                }
            case .failure(let error):
                DispatchQueue.main.async {
                    self?.weatherState = .error("Failed to load weather data: \(error.localizedDescription)")
                }
            }
        }
    }
}

enum WeatherState {
    case loading
    case success(WeatherResponse)
    case error(String)
}
