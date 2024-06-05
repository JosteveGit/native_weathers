import Foundation
import UIKit
import Combine

class HomeViewController: UIViewController, UITextFieldDelegate {
    private var viewModel: WeatherViewModel!
    private var cancellables: Set<AnyCancellable> = []
    
    @IBOutlet weak var cityTextField: UITextField!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        viewModel = WeatherViewModel(repository: WeatherRepository())
        
        // Set the delegate
              cityTextField.delegate = self
              
              // Optionally set other properties
              cityTextField.returnKeyType = .done
        
        activityIndicator.isHidden = true
        if let favoriteCity = UserDefaults.standard.string(forKey: "favoriteCity") {
            cityTextField.text = favoriteCity
        }
    }
    
    @IBAction func getWeatherTapped(_ sender: Any) {
        guard let city = cityTextField.text, !city.isEmpty else { return }
        let apiKey = "eb05a02728f5edd17985006b4c42c07a"
        
        cancellables.forEach { $0.cancel() }
        cancellables.removeAll()

        
        viewModel.getWeather(city: city, apiKey: apiKey)
        
        
        viewModel.$weatherState
            .receive(on: RunLoop.main)
            .sink { [weak self] state in
                guard let self = self else { return }
                print("I am getting some response and this is the state \(state)")
                switch state {
                case .loading:
                    self.showLoader()
                case .success(let response):
                    self.hideLoader()
                    self.performSegue(withIdentifier: "showWeatherDetail", sender: response)
                case .error(let message):
                    self.hideLoader()
                    self.showError(message: message)
                }
            }
            .store(in: &cancellables)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    private func showLoader() {
        activityIndicator.startAnimating()
        activityIndicator.isHidden = false
    }
    
    private func hideLoader() {
        activityIndicator.stopAnimating()
        activityIndicator.isHidden = true
    }
    
    private func handleSuccess(response: WeatherResponse) {
        guard response.weather.first != nil else {
            showError(message: "Invalid weather data")
            return
        }
        performSegue(withIdentifier: "showWeatherDetail", sender: response)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showWeatherDetail",
           let weatherDetailVC = segue.destination as? WeatherDetailViewController,
           let weatherResponse = sender as? WeatherResponse {
            weatherDetailVC.weatherResponse = weatherResponse
            weatherDetailVC.city = cityTextField.text
        }
    }
    
    private func showError(message: String) {
        let alert = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}
