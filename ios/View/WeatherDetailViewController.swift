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

    
    override func viewDidLoad() {
        super.viewDidLoad()
        if let weatherResponse = weatherResponse {
            weatherDescriptionLabel.text = weatherResponse.weather.first?.description
            temperatureLabel.text = "\(weatherResponse.main.temp) °C"
        }
    }

    @IBAction func saveFavoriteCity(_ sender: Any) {
        
                if let favoriteCity = city {
                    UserDefaults.standard.set(favoriteCity, forKey: "favoriteCity")
                    let alert = UIAlertController(title: "Saved", message: "\(city) has been saved as favorite.", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default))
                    present(alert, animated: true)
                }
    }

}
