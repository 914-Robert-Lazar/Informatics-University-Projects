const countiesWithCities = {"Harghita": ["Miercurea-Ciuc", "Odorheiu-Secuiesc", "Vlahita"], "Cluj": ["Cluj-Napoca", "Dej", "Turda", "Huedin"],
        "Covasna": ["Sfantu Gheorghe", "Covasna"]}

function changeContent() {
    const countyComboBox = document.getElementById("county-combobox");
    const currentCounty = countyComboBox.options[countyComboBox.selectedIndex].value;
    const currentCities = countiesWithCities[currentCounty];
    
    const cityComboBox = document.getElementById("city-combobox");
    cityComboBox.innerHTML = "";
    for (city in currentCities) {
        console.log();
        const cityOption = document.createElement("OPTION");
        cityOption.setAttribute("value", currentCities[city]);
        cityOption.text = currentCities[city];

        cityComboBox.add(cityOption);
    }
}