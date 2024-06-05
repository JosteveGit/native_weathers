//
//  WeatherDetailViewController.swift
//  Weather
//
//  Created by Josteve Adekanbi on 05/06/2024.
//

import Foundation


import UIKit

class WeatherDetailViewController: UIViewController {
    @IBOutlet weak var weatherDescriptionLabel: UILabel!
    @IBOutlet weak var temperatureLabel: UILabel!
    var weatherResponse: WeatherResponse?
    var city: String?
    
    @IBOutlet weak var cityLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.tintColor = UIColor.white

        if let weatherResponse = weatherResponse {
            weatherDescriptionLabel.text = weatherResponse.weather.first?.description.capitalized
            temperatureLabel.text = "\(Int(weatherResponse.main.temp - 273))°C"
            cityLabel.text = city?.capitalized
        }
    }
    
    @IBAction func saveFavoriteCity(_ sender: Any) {
        
        if let favoriteCity = city {
            UserDefaults.standard.set(favoriteCity.capitalized, forKey: "favoriteCity")
            let alert = UIAlertController(title: "Saved", message: "\(favoriteCity.capitalized) has been saved as favorite.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default))
            present(alert, animated: true)
        }
    }
    
}
