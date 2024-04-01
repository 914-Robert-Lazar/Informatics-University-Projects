

function getSelected() {
    const form = document.getElementById("forms")
    const element = document.getElementById("dropdown")
    
    
    const previous = document.getElementById("creatorDiv");
    if (previous != null) {
        previous.remove();
    } 
    
    if (element.options[element.selectedIndex].value == "ol") {
        console.log(element.options[element.selectedIndex].text)
        const listCreater = document.createElement("div")
        listCreater.setAttribute("id", "creatorDiv")
        form.appendChild(listCreater)

        const olForm = document.createElement("form")
        olForm.setAttribute("id", "olForm")
        listCreater.appendChild(olForm)
        

        const newInput = document.createElement("input")
        newInput.setAttribute("type", "text")
        olForm.appendChild(newInput)

        const addButton = document.createElement("button")
        addButton.addEventListener("click", addNewListEntry)
        addButton.textContent = "Add"
        olForm.appendChild(addButton)
    }

}

function addNewListEntry(event) {
    event.preventDefault();
    const olForm = document.getElementById("olForm")

    const newInput = document.createElement("input")
    newInput.setAttribute("type", "text")
    olForm.appendChild(newInput)


}